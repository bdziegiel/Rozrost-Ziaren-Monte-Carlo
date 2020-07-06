public class Main {

    static public void main(String[] args){
        Form form = new Form();
        form.setAndShowGUI();
        Controller controller = new Controller(form);
        controller.initController();
    }
}
