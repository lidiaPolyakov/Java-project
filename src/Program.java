import il.ac.shenkar.model.IModel;
import il.ac.shenkar.model.Model;
import il.ac.shenkar.model.ModelException;
import il.ac.shenkar.view.IView;
import il.ac.shenkar.view.View;
import il.ac.shenkar.viewmodel.IViewModel;
import il.ac.shenkar.viewmodel.ViewModel;

import javax.swing.*;

public class Program {

    public static void main(String[] args) {

        IView view = new View();
        try {
            IModel model = new Model();
            IViewModel vm = new ViewModel();
            /* Standalone application */
            SwingUtilities.invokeLater(() -> {
                view.start();
                view.setViewModel(vm);
                vm.setModel(model);
                vm.setView(view);
            });
        } catch (ModelException | ClassNotFoundException e) {
            e.printStackTrace();
            view.setMessage("Can't run the program, " + e.getMessage());
            System.exit(-1);
        }


    }

}
