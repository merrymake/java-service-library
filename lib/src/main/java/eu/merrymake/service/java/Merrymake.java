package eu.merrymake.service.java;

import org.json.JSONObject;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;

public class Merrymake implements MerrymakeInterface {

    public static Merrymake service(String[] args) {
        return new Merrymake(args);
    }

    private static byte[] getPayload() throws IOException {
        return System.in.readAllBytes();
    }

    private final String action;
    private final JSONObject envelope;
    private final byte[] payloadBytes;

    private Merrymake(String[] args) {
        action = args[args.length - 2];
        envelope = new JSONObject(args[args.length - 1]);
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
            var response = client.send(request, HttpResponse.BodyHandlers.discarding());
        } catch (InterruptedException e) {
            throw new RuntimeException("Interrupted contacting rapids.");
        } catch (IOException e) {
            throw new RuntimeException("Could not reach rapids.");
        }
    }

    public static void postToRapids(String event, byte[] body, MimeType contentType) {
        internalPostToRapids(event, req -> req
                .header("Content-Type", contentType.toString())
                .POST(HttpRequest.BodyPublishers.ofByteArray(body)));
    }

    public static void postToRapids(String event, String body, MimeType contentType) {
        internalPostToRapids(event, req -> req
                .header("Content-Type", contentType.toString())
                .POST(HttpRequest.BodyPublishers.ofString(body)));
    }

    public static void postToRapids(String event) {
        internalPostToRapids(event, req -> req
                .POST(HttpRequest.BodyPublishers.noBody()));
    }

    public static void replyToOrigin(byte[] body, MimeType contentType) {
        postToRapids("$reply", body, contentType);
    }

    public static void replyToOrigin(String body, MimeType contentType) {
        postToRapids("$reply", body, contentType);
    }

    public static void replyFileToOrigin(String path, MimeType contentType) throws FileNotFoundException, IOException {
        byte[] data = Merrymake.class.getClassLoader().getResourceAsStream(path).readAllBytes();
        postToRapids("$reply", data, contentType);
    }

    public static void replyFileToOrigin(String path) throws FileNotFoundException, IOException {
        byte[] data = Merrymake.class.getClassLoader().getResourceAsStream(path).readAllBytes();
        MimeType mime = MimeType.ext2mime.get(path.substring(path.lastIndexOf('.') + 1).toLowerCase());
        if (mime == null)
            throw new RuntimeException("Unknown file type. Add mimeType argument.");
        postToRapids("$reply", data, mime);
    }

}
