package eu.merrymake.service.java;

public interface MerrymakeInterface {

    /**
     * Used to link actions in the Merrymake.json file to code.
     * @param action the action from the Merrymake.json file
     * @param handler the code to execute when the action is triggered
     * @return the Merrymake builder to define further actions
     */
    MerrymakeInterface handle(String action, ActionHandler handler);

    /**
     * Used to define code to run after deployment but before release. Useful for smoke tests or database consolidation. Similar to an 'init container'
     * @param handler the code to execute
     */
    void init(InitHandler handler);
}
