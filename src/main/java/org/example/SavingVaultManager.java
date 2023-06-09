package org.example;

import java.util.ArrayList;
import java.util.List;

public class SavingVaultManager {
    private List<SavingVault> savingVaults;

    public SavingVaultManager() {
        this.savingVaults = new ArrayList<>();
    }

    public void createSavingVault(String dream, double dreamAmount) {
        SavingVault savingVault = new SavingVault(dream, dreamAmount);
        savingVaults.add(savingVault);
    }

    public List<SavingVault> getSavingVaults() {
        return savingVaults;
    }

    public void addSavingVault(SavingVault vault){
        savingVaults.add(vault);
    }

    public void removeSavingVault(SavingVault vault) {
        savingVaults.remove(vault);
    }
}
