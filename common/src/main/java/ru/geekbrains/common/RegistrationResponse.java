package ru.geekbrains.common;

public class RegistrationResponse extends AbstractMessage {
    private String regCommand;

    public RegistrationResponse(String regCommand) {
        this.regCommand = regCommand;
    }

   public String getRegCommand() {
        return regCommand;
   }

}
