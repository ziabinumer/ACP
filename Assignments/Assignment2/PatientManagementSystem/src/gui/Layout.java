package gui;

import javax.swing.*;
import java.awt.*;


// creating this as base layout that adminframe and guestframe can extend and implement their own
// purpose is to keep other frames clean and also reduce redundant code
/*
    initial idea:
        Main Container:
            Top Section
                Title Bar
                menu (as applications like vs code has) and toolbar (buttons for methods)
            Content Area
                will decide what to show here
                can add forms and dialogs maybe
        
*/
public abstract class Layout {   
    JPanel mainContainer;
    JPanel contentArea;
    JFrame frame;

    public Layout() {
        frame = new JFrame();
        // will do rest tomorrow. need to study some stuff. man its too big fuck
    }

}