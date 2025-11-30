package gui;

import javax.swing.*;
import java.awt.*;


// creating this as base layout that adminframe and guestframe can extend and implement their own
// purpose is to keep other frames clean and also reduce redundant code
/*
    initial idea:
        Main Container:
            Top Section
                menu (as applications like vs code has) and toolbar (buttons for methods)
            Content Area
                will decide what to show here
                can add forms and dialogs maybe
        
*/
public abstract class Layout extends JFrame {   
    JPanel mainContainer;
    JPanel contentArea;

    public Layout(String title) {
        setTitle(title);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        // main
        mainContainer = new JPanel(new BorderLayout());
        
        // menu
        JPanel menu = createMenu();
        if (menu != null) {
            mainContainer.add(menu, BorderLayout.NORTH);
        }

        // content area
        contentArea = new JPanel(new BorderLayout());
        mainContainer.add(contentArea, BorderLayout.CENTER);

        //
        add(mainContainer);
        
        initializeContent();

    }

    protected abstract JPanel createMenu();

    protected abstract void initializeContent();

}