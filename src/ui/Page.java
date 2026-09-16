package ui;

public interface Page {

    void show();

     int TOTAL_WIDTH = 65;


    default void displayHeader(String title) {
        System.out.println("\n\n" + String.valueOf("╔═").concat(line('═')).concat("═╗"));
        System.out.println(center(title));
        System.out.println(String.valueOf("╚═").concat(line('═')).concat("═╝\n\n"));

    }

    default void success (String message) {
        System.out.println("\n✔ " + message);
    }

    default void error(String message) {
        System.out.println("\n✘ " + message);
    }

    default void subHeader(String title) {
        System.out.println(center("[ " + title + " ]"));
        System.out.println("\n");
    }

    default String line(char c) {
        return String.valueOf(c).repeat(TOTAL_WIDTH);
    }

    private String center(String title) {
        if(title == null)
            return "";
        String formattedTitle = title.trim().toUpperCase();
        int titleLength = formattedTitle.length();

        if (titleLength >= TOTAL_WIDTH) {
            return formattedTitle.substring(0, TOTAL_WIDTH);
        }

        int totalPaddingNeeded = TOTAL_WIDTH - titleLength;
        int leftPaddingSize = totalPaddingNeeded / 2;
        int rightPaddingSize = totalPaddingNeeded - leftPaddingSize;

        String leftPadding = " ".repeat(leftPaddingSize);
        String rightPadding = " ".repeat(rightPaddingSize);

        return leftPadding + formattedTitle + rightPadding;
    }
}
