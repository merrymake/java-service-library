package eu.merrymake.service.java;

public interface MerrymakeInterface {
    MerrymakeInterface handle(String action, ActionHandler handler);
    void init(InitHandler handler);
}
