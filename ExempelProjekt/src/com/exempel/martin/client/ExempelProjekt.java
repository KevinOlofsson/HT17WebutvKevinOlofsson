package com.exempel.martin.client;

import java.util.ArrayList;

import com.google.gwt.core.client.EntryPoint;
import com.google.gwt.event.dom.client.KeyCodes;
import com.google.gwt.event.dom.client.KeyDownEvent;
import com.google.gwt.event.dom.client.KeyDownHandler;
import com.google.gwt.event.dom.client.MouseDownEvent;
import com.google.gwt.event.dom.client.MouseDownHandler;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.FlexTable;
import com.google.gwt.user.client.ui.FocusPanel;
import com.google.gwt.user.client.ui.HorizontalPanel;
import com.google.gwt.user.client.ui.MultiWordSuggestOracle;
import com.google.gwt.user.client.ui.RootPanel;
import com.google.gwt.user.client.ui.SuggestBox;
import com.google.gwt.user.client.ui.TextBox;
import com.google.gwt.user.client.ui.VerticalPanel;

/**
 * Entry point classes define <code>onModuleLoad()</code>.
 */

public class ExempelProjekt implements EntryPoint {
	private VerticalPanel mainPanel = new VerticalPanel();
	private HorizontalPanel addPanel = new HorizontalPanel();
	private TextBox operand1TextBox = new TextBox();
	private TextBox operand2TextBox = new TextBox();
	private Button calculateButton = new Button("Calculate");
	private MultiWordSuggestOracle oracle = new MultiWordSuggestOracle();
	private SuggestBox operatorTextBox = new SuggestBox(oracle);
	private FocusPanel enterKey = new FocusPanel();
	private ArrayList<String> calcValues = new ArrayList<String>();
	private FlexTable calcTable = new FlexTable();

	/**
	 * Entry point method.
	 */
	public void onModuleLoad() {
		// Suggests the valid operators
		oracle.add("*");
		oracle.add("%");
		oracle.add("+");
		oracle.add("-");
		//flextable text
		calcTable.setText(2, 1, "Answers:");
		calcTable.setText(2, 2, "");

		addPanel.add(operand1TextBox);
		addPanel.add(operatorTextBox);
		addPanel.add(operand2TextBox);
		addPanel.add(calculateButton);

		// TODO Assemble Main panel.
		enterKey.add(addPanel);
		mainPanel.add(enterKey);
		mainPanel.add(calcTable);
		// TODO Associate the Main panel with the HTML host page.
		RootPanel.get("calc").add(mainPanel);
		// TODO Move cursor focus to the input box.

		
		// Calculate button function
        calculateButton.addMouseDownHandler(new MouseDownHandler() {
            public void onMouseDown(MouseDownEvent event) {
                if (event.getNativeButton() == 1) {
                    calculate();
                }
            }
        });
        
		//Calculate by pressing enter
		enterKey.addKeyDownHandler(new KeyDownHandler() {
			public void onKeyDown(KeyDownEvent event) {
				if (event.getNativeKeyCode() == KeyCodes.KEY_ENTER) {
					calculate();
				}
			}
		});
	}

	private void calculate() {
		final String operator = operatorTextBox.getText().trim();
		calculateButton.setFocus(true);
		if ((!operator.equals("*") && !operator.equals("+") && !operator.equals("%")&& !operator.equals("-"))
				|| !isInteger(operand1TextBox.getText().trim()) || !isInteger(operand2TextBox.getText().trim())) {
			Window.alert("You have entered a non valid binary operator or one of the operands is not an integer");

			return;
		}

		int operand1 = Integer.parseInt(operand1TextBox.getText());
		int operand2 = Integer.parseInt(operand2TextBox.getText());
		int answer= 0;
		
		// Multiplication
		if (operator.equals("*")) {
			answer= multiplication(operand1,operand2);
		}
		// Modulo
		else if (operator.equals("%")) {
			answer= modulo(operand1,operand2);
		}
		//Subtraktion
		else if (operator.equals("-")) {
			answer= subtraction(operand1,operand2);
		}
		// addition
		else {
			answer= addition(operand1,operand2);
		}
		addValuesToTable(operand1,operand2,answer,operator);
	}
	//multiplication method
	private int multiplication(int operand1,int operand2){
		int answer = operand1 * operand2;
		Window.alert("The answer is: " + answer);
		return answer;
	}
	//addition method
	private int addition(int operand1,int operand2){
		int answer = operand1 + operand2;
		Window.alert("The answer is: " + answer);
		return answer;
	}
	//modulo method
	private int modulo(int operand1,int operand2){
		int answer = operand1 % operand2;
		Window.alert("The answer is: " + answer);
		return answer;
	}
	//subtraction method
	private int subtraction(int operand1,int operand2){
		int answer = operand1 - operand2;
		Window.alert("The answer is: " + answer);
		return answer;
	}
	//flextable
	private void addValuesToTable(int operand1, int operand2, int answer, String operator) {
        
        String operand1InString = Integer.toString(operand1);
        String operand2InString = Integer.toString(operand2);
        String answerToString =Integer.toString(answer);
        
        final String symbol = operand1InString + " " + operator + " " + operand2InString + " = " + answerToString;
        
        if (calcValues.contains(symbol))
            return;
        
        int row = calcTable.getRowCount();
        calcValues.add(symbol);
        calcTable.setText(row,0,symbol);
    }

	// Checkes if a String could be seen as an integer
	public boolean isInteger(String input) {
		try {
			Integer.parseInt(input);
			return true;
		} catch (NumberFormatException e) {
			return false;
		}
	}

}