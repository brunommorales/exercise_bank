package org.example;

import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.button.ButtonVariant;
import com.vaadin.flow.component.formlayout.FormLayout;
import com.vaadin.flow.component.notification.Notification;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.component.radiobutton.RadioButtonGroup;
import com.vaadin.flow.router.Route;

import entities.Account;

@Route
public class Program extends VerticalLayout {
    private final TextField numberField = new TextField("Account Number");
    private final TextField holderField = new TextField("Account Holder");
    private final RadioButtonGroup<String> initialDepositChoice = new RadioButtonGroup<>();
    private final TextField initialDepositField = new TextField("Initial Deposit");
    private final TextField depositValueField = new TextField("Deposit Value");
    private final TextField withdrawValueField = new TextField("Withdraw Value");
    private final Button submitButton = new Button("Submit");
    private final TextField resultField = new TextField("Result");

    private Account account;

    public Program() {
        FormLayout formLayout = new FormLayout();

        initialDepositChoice.setLabel("Initial Deposit?");
        initialDepositChoice.setItems("Yes", "No");

        formLayout.add(numberField, holderField, initialDepositChoice, initialDepositField, depositValueField, withdrawValueField, submitButton, resultField);

        submitButton.addThemeVariants(ButtonVariant.LUMO_PRIMARY);
        submitButton.addClickListener(e -> submitForm());

        add(formLayout);
    }

    private void submitForm() {
        int number = Integer.parseInt(numberField.getValue());
        String holder = holderField.getValue();

        String initialDepositChoice = this.initialDepositChoice.getValue();
        double initialDeposit = initialDepositChoice.equals("Yes") ? Double.parseDouble(initialDepositField.getValue()) : 0.0;

        if (initialDepositChoice.equals("Yes")) {
            account = new Account(number, holder, initialDeposit);
        } else {
            account = new Account(number, holder);
        }

        double depositValue = Double.parseDouble(depositValueField.getValue());
        account.deposit(depositValue);

        double withdrawValue = Double.parseDouble(withdrawValueField.getValue());
        account.withdraw(withdrawValue);

        resultField.setValue(account.toString());
        showNotification("Account data updated!");
    }

    private void showNotification(String message) {
        Notification.show(message, 3000, Notification.Position.TOP_CENTER);
    }
}
