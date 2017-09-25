
package org.onebeartoe.development.tools.html.utility.panels;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;
import javax.swing.JCheckBox;
import javax.swing.JPanel;
import javax.swing.JSplitPane;
import javax.swing.JTextField;
import javax.swing.border.Border;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import org.onebeartoe.application.ui.GUITools;
import org.onebeartoe.application.ui.swing.ScrollableTextArea;

/**
 * This class works on lists.  It modifies the original list by creating HTML 
 * anchor tags and/or converting the list to an HTML unordered list.
 * 
 * @author Roberto Marquez
 */
public class ListsPanel extends JPanel implements DocumentListener
{
    private JTextField targetDirectory;

    private final ScrollableTextArea inputTextArea;

    private final ScrollableTextArea outputTextArea;

    private JCheckBox anchorTagCheckBox;

    private JCheckBox unorderedListCheckBox;
    
    public ListsPanel()
    {
        Border border = GUITools.factoryLineBorder("Input");
        
        targetDirectory = new JTextField();
        targetDirectory.addActionListener( new ActionListener() 
        {
            @Override
            public void actionPerformed(ActionEvent e) 
            {
                System.out.println("inso");

            }
        });
  
        inputTextArea = new ScrollableTextArea("");
        inputTextArea.addTextListener(this);
        
        JPanel inputPanel = new JPanel(new BorderLayout());
        inputPanel.setBorder(border);
        inputPanel.add(inputTextArea, BorderLayout.CENTER);
        inputPanel.setMinimumSize( new Dimension(300, 1) );
        
        outputTextArea = new ScrollableTextArea("\n\n");

        JPanel outputPanel = new JPanel();
        outputPanel.setLayout(new BorderLayout());
        outputPanel.add(outputTextArea, BorderLayout.CENTER);

        Border border3 = GUITools.factoryLineBorder("Output");
        outputPanel.setBorder(border3);
        outputPanel.setMinimumSize( new Dimension(300, 1) );
        
        anchorTagCheckBox = new JCheckBox("Anchor Tags");
        anchorTagCheckBox.addActionListener( new ActionListener()
        {
            @Override
            public void actionPerformed(ActionEvent e)
            {
                updateOutput();
            }
        });
        unorderedListCheckBox = new JCheckBox("Unordered List");
        unorderedListCheckBox.addActionListener( new ActionListener()
        {
            @Override
            public void actionPerformed(ActionEvent e)
            {
                updateOutput();
            }
        });
        JPanel checkboxPanel = new JPanel( new FlowLayout(FlowLayout.CENTER, 20, 20) );
        checkboxPanel.add(anchorTagCheckBox);
        checkboxPanel.add(unorderedListCheckBox);

        // define the JFrame content layout        
        setLayout(new BorderLayout());
        JSplitPane splitPane = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT, inputPanel, outputPanel);
        splitPane.setOneTouchExpandable(true);
        add(splitPane, BorderLayout.CENTER);
        add(checkboxPanel, BorderLayout.SOUTH);
    }
    

    @Override
    /**
     * This method does not fire for a plain text document like JTextArea, as it is 
     * a PlainDocument and not a StyledDocument.
     * 
     *         http://docs.oracle.com/javase/tutorial/uiswing/events/documentlistener.html
     * 
     */
    public void changedUpdate(DocumentEvent e)
    {
        // do nothing 
    }    

    @Override
    public void insertUpdate(DocumentEvent e)
    {
        updateOutput();
    }

    @Override
    public void removeUpdate(DocumentEvent e)
    {
        updateOutput();
    }
    
    private void updateOutput()
    {
        String input = inputTextArea.getText();
        
        // Split the input into strings separated by new lines, ignoring blank lines (the '+' in the regular expression)
        //
        // Apparently starting with java 8, the regular expression "\R" works as a Unicode catch all for new lines.
        String[] strs = input.split("[\\r\\n]+");
        
        List<String> list = Arrays.asList(strs);

        if( anchorTagCheckBox.isSelected() )
        {
            list = list.stream()
                       .map(s -> "<a href=''alt=''>" + s + "</a>")
                       .collect( Collectors.toList() );
        }
        
        if( unorderedListCheckBox.isSelected() )
        {
            list = list.stream()
                       .map(s -> "\t<li>" + s + "</li>")
                       .collect(Collectors.toList() );
            
            // add the open and close tag
            list.add(0, "<ul>");
            list.add("</ul>");
        }
        
        String delimitor = System.lineSeparator();
        String output = String.join(delimitor, list);
        
        outputTextArea.setText(output);
    }
}
