package org.example;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.mock;

class SavingVaultManagerTest {

    private SavingVaultManager savingVaultManager;

    @BeforeEach
    void beforeEach() {
        savingVaultManager = new SavingVaultManager();
    }
    @Test
    public void testCreateSavingVault() {
        // Arrange
        String dream = "Vacation";
        double dreamAmount = 5000.0;

        // Act
        savingVaultManager.createSavingVault(dream, dreamAmount);
        List<SavingVault> savingVaults = savingVaultManager.getSavingVaults();

        // Assert
        assertEquals(1, savingVaults.size());
        SavingVault savingVault = savingVaults.get(0);
        assertEquals(dream, savingVault.getDream());
        assertEquals(dreamAmount, savingVault.getDreamAmount());
        assertEquals(0.0, savingVault.getSavings());
    }

    @Test
    public void testAddSavingVault() {
        // Arrange
        SavingVault savingVault = mock(SavingVault.class);

        // Act
        savingVaultManager.addSavingVault(savingVault);
        List<SavingVault> savingVaults = savingVaultManager.getSavingVaults();

        // Assert
        assertEquals(1, savingVaults.size());
        assertTrue(savingVaults.contains(savingVault));
    }

    @Test
    public void testRemoveSavingVault() {
        // Arrange
        SavingVault savingVault = mock(SavingVault.class);
        savingVaultManager.addSavingVault(savingVault);

        // Act
        savingVaultManager.removeSavingVault(savingVault);
        List<SavingVault> savingVaults = savingVaultManager.getSavingVaults();

        // Assert
        assertEquals(0, savingVaults.size());
        assertFalse(savingVaults.contains(savingVault));
    }

}