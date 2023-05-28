package ru.nethology

import additionalCommissionMaster
import maxMountLimitOtherCard
import maxMountLimitOtherCardText
import maxMountLimitVkPay
import maxNoTaxMountMaster
import maximumInOneTransaction
import maximumInOneTransactionCards
import maximumInOneTransactionText
import minCommissionVisa
import translations
import org.junit.Test

import org.junit.Assert.*
import taxMaster

class MainKtTest {
    @Test
    fun maxMountLimitOtherCardFalse() {
        val amountOfPreviousTransfersMonth = 12000
        val transferAmount = 100000
        val maxMountLimitOtherCards = 600_000

        val result = maxMountLimitOtherCard(amountOfPreviousTransfersMonth, transferAmount, maxMountLimitOtherCards)

        assertEquals(false, result)
    }

    @Test
    fun maxMountLimitOtherCardTrue() {
        val amountOfPreviousTransfersMonth = 12000
        val transferAmount = 100000
        val maxMountLimitOtherCards = 60_000

        val result = maxMountLimitOtherCard(amountOfPreviousTransfersMonth, transferAmount, maxMountLimitOtherCards)

        assertEquals(true, result)
    }

    @Test
    fun maxMountLimitOtherCardTextTrue() {
        val amountOfPreviousTransfersMonth = 45_000
        val maxMountLimitOtherCard = 35_000

        val result = maxMountLimitOtherCardText(amountOfPreviousTransfersMonth, maxMountLimitOtherCard)

        assertEquals(
            "Лимит транзакций превышен! Вы не можете совершать переводы по этой платёжной системе до конца месяца.",
            result
        )

    }

    @Test
    fun maxMountLimitOtherCardTextFalse() {
        val amountOfPreviousTransfersMonth = 43_000
        val maxMountLimitOtherCard = 44_000

        val result = maxMountLimitOtherCardText(amountOfPreviousTransfersMonth, maxMountLimitOtherCard)

        assertEquals(
            "Лимит транзакций превышен! Остаток для переводов в месяц составляет " + (maxMountLimitOtherCard - amountOfPreviousTransfersMonth) + " руб.",
            result
        )
    }

    @Test
    fun maximumInOneTransactionFalse() {
        val transferAmount = 50_000
        val maximumInOneTransactionCards = 60_000

        val result = maximumInOneTransaction(maximumInOneTransactionCards, transferAmount)

        assertEquals(false, result)
    }

    @Test
    fun maximumInOneTransactionTrue() {
        val transferAmount = 50_000
        val maximumInOneTransactionCards = 40_000

        val result = maximumInOneTransaction(maximumInOneTransactionCards, transferAmount)

        assertEquals(true, result)
    }

    @Test
    fun maximumInOneTransactionTextOnce() {
        val maximumInOneTransactionCards = 150
        val result = maximumInOneTransactionText(maximumInOneTransactionCards)

        assertEquals(
            "Лимит перевода за одну транзакцию превышен! Пожалуйста уменьшите сумму перевода до $maximumInOneTransactionCards руб",
            result
        )
    }

    @Test
    fun translationsFirstPositive() {
        val amountOfPreviousTransfersMonth = 29_000
        val transferAmount = 19000
        val result = translations(amountOfPreviousTransfersMonth, transferAmount)

        assertEquals(
            "Лимит перевода за одну транзакцию превышен! Пожалуйста уменьшите сумму перевода до 15000 руб",
            result
        )
    }

    @Test
    fun translationsTwoPositive() {
        val amountOfPreviousTransfersMonth = 12_000
        val transferAmount = 10_000
        val cardType = "Visa"
        val result = translations(amountOfPreviousTransfersMonth, transferAmount, cardType)

        assertEquals("Комиссия 75.0 руб", result)
    }

    @Test
    fun translationsVisaTreePositive() {
        val amountOfPreviousTransfersMonth = 10_000
        val transferAmount = 2_000_000
        val cardType = "Visa"
        val result = translations(amountOfPreviousTransfersMonth, transferAmount, cardType)

        assertEquals(maximumInOneTransactionText(maximumInOneTransactionCards), result)
    }

    @Test
    fun translationsTreePositive() {
        val amountOfPreviousTransfersMonth = 12_000
        val transferAmount = 10_000
        val cardType = "Mastercard"
        val result = translations(amountOfPreviousTransfersMonth, transferAmount, cardType)

        assertEquals("Комиссия не взимается", result)
    }

    @Test
    fun translationsOneUpperMountLimit() {
        val amountOfPreviousTransfersMonth = 590_000
        val transferAmount = 18_000
        val cardType = "Mastercard"
        val result = translations(amountOfPreviousTransfersMonth, transferAmount, cardType)

        assertEquals("Лимит транзакций превышен! Остаток для переводов в месяц составляет 10000 руб.", result)
    }

    @Test
    fun translationsOneUpperMountLimitMax() {
        val amountOfPreviousTransfersMonth = 690_000
        val transferAmount = 18_000
        val cardType = "Mastercard"
        val result = translations(amountOfPreviousTransfersMonth, transferAmount, cardType)

        assertEquals(
            "Лимит транзакций превышен! Вы не можете совершать переводы по этой платёжной системе до конца месяца.",
            result
        )
    }

    @Test
    fun translationsMaserIfTrue() {
        val amountOfPreviousTransfersMonth = 10_000
        val transferAmount = 1_800_000
        val cardType = "Mastercard"
        val result = translations(amountOfPreviousTransfersMonth, transferAmount, cardType)

        assertEquals(
            maximumInOneTransactionText(maximumInOneTransactionCards),
            result
        )
    }
    @Test
    fun translationsVkPayNoCommision() {
        val amountOfPreviousTransfersMonth = 35_000
        val transferAmount = 5_000
        val result = translations(amountOfPreviousTransfersMonth, transferAmount)
        assertEquals(
            "Комиссия не взимается",
            result
        )
    }
    @Test
    fun translationsMaserTransferUpperLimit() {
        val amountOfPreviousTransfersMonth = 75_000
        val transferAmount = 10_000
        val cardType = "Mastercard"
        val result = translations(amountOfPreviousTransfersMonth, transferAmount, cardType)

        assertEquals(
            "Комиссия " + (transferAmount * taxMaster + additionalCommissionMaster) + " руб",
        result
        )
    }

    @Test
    fun translationsVisaElsePositive() {
        val amountOfPreviousTransfersMonth = 10_000
        val transferAmount = 2_000
        val cardType = "Visa"
        val result = translations(amountOfPreviousTransfersMonth, transferAmount, cardType)

        assertEquals("Комиссия $minCommissionVisa руб", result)
    }

    @Test
    fun translationsMaserIfTrueMax() {
        val amountOfPreviousTransfersMonth = 60_000
        val transferAmount = 20_000
        val cardType = "Mastercard"
        val result = translations(amountOfPreviousTransfersMonth, transferAmount, cardType)

        val balanceAfterLimit: Int = (amountOfPreviousTransfersMonth + transferAmount) - maxNoTaxMountMaster

        assertEquals(
            "Комиссия " + (balanceAfterLimit * taxMaster + additionalCommissionMaster) + " руб",
            result
        )
    }
    @Test
    fun translationsVisaReturnIf() {
        val amountOfPreviousTransfersMonth = 599_000
        val transferAmount = 2_000
        val cardType = "Visa"
        val result = translations(amountOfPreviousTransfersMonth, transferAmount, cardType)

        assertEquals(maxMountLimitOtherCardText(amountOfPreviousTransfersMonth, maxMountLimitOtherCard ), result)
    }

    @Test
    fun translationsVisaReturnIfClose() {
        val amountOfPreviousTransfersMonth = 400_001
        val transferAmount = 2_000

        val result = 1
            //translations(amountOfPreviousTransfersMonth, transferAmount)

        assertEquals(maxMountLimitOtherCardText(amountOfPreviousTransfersMonth, maxMountLimitVkPay), result)
    }







}