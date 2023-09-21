package eu.merrymake.service.java;

public class NullMerrymake implements MerrymakeInterface {
    NullMerrymake(){}
    public MerrymakeInterface handle(String action, ActionHandler handler){return this;}
    public void init(InitHandler handler){}
}
