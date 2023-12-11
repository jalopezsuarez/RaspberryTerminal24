package com.control.ui.application;

import java.awt.Color;
import java.awt.Component;
import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.Cursor;
import java.awt.Point;

import javax.swing.BorderFactory;
import javax.swing.JScrollBar;
import javax.swing.JScrollPane;
import javax.swing.ScrollPaneConstants;

import com.control.ui.components.ScrollbarUI;

public abstract class ViewController extends JScrollPane
{
    // Class Methods
    // ===============================================================

    private static final long serialVersionUID = 645060494281999861L;

    protected NavigationController navigationController;

    protected String title;

    public ViewController()
    {
        setBackground(Color.decode("#ffffff"));
        setBorder(BorderFactory.createEmptyBorder());
        setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);
        setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_AS_NEEDED);

        JScrollBar scrollBar = this.getVerticalScrollBar();
        scrollBar.setUI(new ScrollbarUI());

        this.viewDidLoad();

        this.addComponentListener(new ComponentAdapter()
        {
            @Override
            public void componentShown(ComponentEvent evt)
            {
                Component c = (Component) evt.getSource();
                ((ViewController) c).viewDidAppear();
            }

            @Override
            public void componentHidden(ComponentEvent evt)
            {
                Component c = (Component) evt.getSource();
                ((ViewController) c).viewDidDisappear();
            }
        });

        // -------------------------------------------------------
        JScrollPane scroller = this;
        scroller.setAutoscrolls(false);

        MouseAdapter mouseAdapter = new MouseAdapter()
        {
            private Point lastDragPoint = null;

            @Override
            public void mouseDragged(MouseEvent e)
            {
                if (lastDragPoint == null)
                {
                    lastDragPoint = e.getPoint();
                }

                Point p = e.getPoint();
                int diffy = (p.y - lastDragPoint.y) / 2;

                if (Math.abs(diffy) < 64)
                {
                    lastDragPoint = e.getPoint();
                    scroller.getVerticalScrollBar().setValue(getVerticalScrollBar().getValue() - diffy);
                    lastDragPoint.y = lastDragPoint.y - diffy;
                }
                e.consume();
            }

            @Override
            public void mousePressed(MouseEvent e)
            {
                scroller.setCursor(new Cursor(Cursor.HAND_CURSOR));
                lastDragPoint = e.getPoint();
                e.consume();
            }

            @Override
            public void mouseReleased(MouseEvent e)
            {
                scroller.setCursor(new Cursor(Cursor.DEFAULT_CURSOR));
                lastDragPoint = null;
                e.consume();
            }

        };

        scroller.getViewport().addMouseListener(mouseAdapter);
        scroller.getViewport().addMouseMotionListener(mouseAdapter);
    }

    // Class Methods
    // ===============================================================
    public NavigationController getNavigationController()
    {
        return navigationController;
    }

    public void setNavigationController(NavigationController navigationController)
    {
        this.navigationController = navigationController;
    }

    // Class Methods
    // ===============================================================
    public void setTitle(String title)
    {
        this.title = title;
    }

    @Override
    public String toString()
    {
        return title;
    }

    // Class Methods
    // ===============================================================
    public abstract void viewDidLoad();

    // Class Methods
    // ===============================================================
    public abstract void viewDidAppear();

    public abstract void viewDidDisappear();

    // Class Methods
    // ===============================================================
    public abstract void backButtonAction();

    public abstract void leftButtonAction();

    public abstract void doneButtonAction();

    public abstract void rightButtonAction();

    // Class Methods
    // ===============================================================
}
