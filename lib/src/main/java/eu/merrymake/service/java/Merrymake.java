package eu.merrymake.service.java;

import org.json.JSONStringer;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;

/**
 * Merrymake is the main class of this library, as it exposes all other
 * functionality, through a builder pattern.
 *
 * @author Merrymake.eu (Chirstian Clausen, Nicolaj Græsholt)
 */
public class Merrymake implements MerrymakeInterface {

    /**
     * This is the root call for a Merrymake service.
     *
     * @param args the arguments from the main method
     * @return a Merrymake builder to make further calls on
     */
    public static Merrymake service(String[] args) {
        return new Merrymake(args);
    }

    private static byte[] getPayload() throws IOException {
        return System.in.readAllBytes();
    }

    private final String action;
    private final Envelope envelope;
    private final byte[] payloadBytes;

    private Merrymake(String[] args) {
        action = args[args.length - 2];
        envelope = new Envelope(args[args.length - 1]);
        try {
            payloadBytes = getPayload();
        } catch (IOException e) {
            throw new RuntimeException("Could not read from stdin");
        }
    }

    public MerrymakeInterface handle(String action, ActionHandler handler) {
        if (this.action.equals(action)) {
            handler.execute(this.payloadBytes, this.envelope);
            return new NullMerrymake();
        } else
            return this;
    }

    public void init(InitHandler handler) {
        handler.execute();
    }

    private interface RequestCompleter {
        HttpRequest.Builder fix(HttpRequest.Builder req);
    }

    private static void internalPostToRapids(String event, RequestCompleter requestCompleter) {
        try {
            var client = HttpClient.newBuilder().version(HttpClient.Version.HTTP_1_1).build();
            var request = requestCompleter.fix(HttpRequest
                    .newBuilder(URI.create(System.getenv("RAPIDS") + "/" + event))).build();
            client.send(request, HttpResponse.BodyHandlers.discarding());
        } catch (InterruptedException e) {
            throw new RuntimeException("Interrupted contacting rapids.");
        } catch (IOException e) {
            throw new RuntimeException("Could not reach rapids.");
        }
    }

    /**
     * Post an event to the central message queue (Rapids), with a payload and its
     * content type.
     *
     * @param event       the event to post
     * @param body        the payload
     * @param contentType the content type of the payload
     */
    public static void postToRapids(String event, byte[] body, MimeType contentType) {
        internalPostToRapids(event, req -> req
                .header("Content-Type", contentType.toString())
                .POST(HttpRequest.BodyPublishers.ofByteArray(body)));
    }

    /**
     * Post an event to the central message queue (Rapids), with a payload and its
     * content type.
     *
     * @param event       the event to post
     * @param body        the payload
     * @param contentType the content type of the payload
     */
    public static void postToRapids(String event, String body, MimeType contentType) {
        internalPostToRapids(event, req -> req
                .header("Content-Type", contentType.toString())
                .POST(HttpRequest.BodyPublishers.ofString(body)));
    }

    /**
     * Post an event to the central message queue (Rapids), without a payload.
     *
     * @param event the event to post
     */
    public static void postToRapids(String event) {
        internalPostToRapids(event, req -> req
                .POST(HttpRequest.BodyPublishers.noBody()));
    }

    /**
     * Post a reply back to the originator of the trace, with a payload and its
     * content type.
     *
     * @param body        the payload
     * @param contentType the content type of the payload
     */
    public static void replyToOrigin(byte[] body, MimeType contentType) {
        postToRapids("$reply", body, contentType);
    }

    /**
     * Post a reply back to the originator of the trace, with a payload and its
     * content type.
     *
     * @param body        the payload
     * @param contentType the content type of the payload
     */
    public static void replyToOrigin(String body, MimeType contentType) {
        postToRapids("$reply", body, contentType);
    }

    /**
     * Send a file back to the originator of the trace.
     *
     * @param path        the path to the file starting from main/resources
     * @param contentType the content type of the file
     */
    public static void replyFileToOrigin(String path, MimeType contentType) throws FileNotFoundException, IOException {
        try (var resource = Merrymake.class.getClassLoader().getResourceAsStream(path)) {
            if (resource == null)
                throw new FileNotFoundException();
            byte[] data = resource.readAllBytes();
            postToRapids("$reply", data, contentType);
        }
    }

    /**
     * Send a file back to the originator of the trace.
     *
     * @param path the path to the file starting from main/resources
     */
    public static void replyFileToOrigin(String path) throws FileNotFoundException, IOException {
        MimeType mime = MimeType.ext2mime.get(path.substring(path.lastIndexOf('.') + 1).toLowerCase());
        if (mime == null)
            throw new RuntimeException("Unknown file type. Add mimeType argument.");
        replyFileToOrigin(path, mime);
    }

    /**
     * Subscribe to a channel, so events will stream back messages broadcast to that
     * channel. You can join multiple channels. You stay in the channel until the
     * request is terminated.
     *
     * Note: The origin-event has to be set as "streaming: true" in the
     * event-catalogue.
     *
     * @param channel the channel to join
     */
    public static void joinChannel(String channel) {
        postToRapids("$join", channel, MimeType.txt);
    }

    /**
     * Broadcast a message (event and payload) to all listeners in a channel.
     *
     * @param to      the channel to broadcast to
     * @param event   the event-type of the message
     * @param payload the payload of the message
     */
    public static void broadcastToChannel(String to, String event, String payload) {
        postToRapids("$broadcast", new JSONStringer().object()
                .key("to").value(to)
                .key("event").value(event)
                .key("payload").value(payload)
                .endObject().toString(), MimeType.json);
    }
}
