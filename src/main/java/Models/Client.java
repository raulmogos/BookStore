package Models;

public class Client extends BaseEntity<Long> {
    private Long id;
    private String firstName;
    private String lastName;
    private int moneySpent;

    public Client() {
    }

    public Client(Long id, String firstName, String lastName, int moneySpent) {
        this.id = id;
        this.firstName = firstName;
        this.lastName = lastName;
        this.moneySpent = moneySpent;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public int getMoneySpent() {
        return moneySpent;
    }

    public void setMoneySpent(int moneySpent) {
        this.moneySpent = moneySpent;
    }

    @Override
    public Long getId() {
        return id;
    }

    @Override
    public void setId(Long id) {
        this.id = id;
    }

    @Override
    public String toString() {
        return "Client - ID: " + id + ", firstName: " + firstName + ", lastName: " + lastName + ", moneySpent: " + moneySpent + ";";
    }
}
