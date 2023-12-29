package eu.merrymake.service.java;

public interface ActionHandler {
    void execute(byte[] payloadBytes, Envelope envelope);
}
