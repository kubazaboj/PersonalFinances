package org.example;

import java.util.ArrayList;
import java.util.List;

public class InvestmentManager {
    private List<Investment> investments;

    public InvestmentManager() {
        this.investments = new ArrayList<>();
    }

    public void addInvestment(Investment investment) {
        investments.add(investment);
    }

    public void removeInvestment(Investment investment) {
        investments.remove(investment);
    }

    public List<Investment> getAllInvestments() {
        return investments;
    }

    public Investment getInvestmentByName(String name) {
        for (Investment investment : investments) {
            if (investment.getName().equals(name)) {
                return investment;
            }
        }
        System.err.println("This investment does not exist.");
        return null;
    }
}