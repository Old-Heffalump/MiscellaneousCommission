fun main() {
    println(translations(12000, 10000, "Visa"))
    println(translations(0, 170_000, "Мир"))
    println(translations(29_000, 19000, ))
    println(translations(5_000, 18000, "Mastercard"))
    println(translations(75_000, 10000, "Mastercard"))


}

const val maxNoTaxMountMaster: Int = 75_000
const val taxMaster: Double = 0.006
const val taxMountVisa: Double = 0.0075
const val minCommissionVisa: Int = 35
const val maxMountLimitVkPay = 40_000
const val maxMountLimitOtherCard = 600_000
const val additionalCommissionMaster = 20
const val maximumInOneTransactionCards = 150_000
const val maximumInOneTransactionVKPay = 15_000

fun maxMountLimitOtherCard(amountOfPreviousTransfersMonth: Int, transferAmount: Int, maxMountLimitOtherCards: Int): Boolean {
    return maxMountLimitOtherCards < (amountOfPreviousTransfersMonth + transferAmount)
}

fun maxMountLimitOtherCardText(amountOfPreviousTransfersMonth: Int, maxMountLimitOtherCard: Int): String {
    return if (amountOfPreviousTransfersMonth > maxMountLimitOtherCard){
        // Добавил условие проверки превышения лимита уже в amountOfPreviousTransfersMonth
        "Лимит транзакций превышен! Вы не можете совершать переводы по этой платёжной системе до конца месяца."
    } else {
    "Лимит транзакций превышен! Остаток для переводов в месяц составляет " + (maxMountLimitOtherCard - amountOfPreviousTransfersMonth) + " руб."
}}

fun maximumInOneTransaction(maximumInOneTransactionCards: Int, transferAmount: Int): Boolean {
    // сравнение максимально возможного перевода за раз и суммы транзакции
    return transferAmount > maximumInOneTransactionCards
}

fun maximumInOneTransactionText(maximumInOneTransactionCards: Int): String {
    // Текстовое сообщение с максимальной суммой перевода
    return "Лимит перевода за одну транзакцию превышен! Пожалуйста уменьшите сумму перевода до $maximumInOneTransactionCards руб"
}

fun translations(amountOfPreviousTransfersMonth: Int = 0, transferAmount: Int, cardType: String = "VkPay"): String {

    when (cardType) {
        "Mastercard", "Maestro" -> {
            if (maximumInOneTransaction(maximumInOneTransactionCards,transferAmount ))  {
                return maximumInOneTransactionText(maximumInOneTransactionCards)
            }
            return if (maxMountLimitOtherCard( // Условие превышения лимита месячных транзакций
                    amountOfPreviousTransfersMonth,
                    transferAmount,
                    maxMountLimitOtherCard
                )
            ) {
                (maxMountLimitOtherCardText(  // текст который должен вернутся
                    amountOfPreviousTransfersMonth,
                    maxMountLimitOtherCard,
                ))

            } else {
                if (amountOfPreviousTransfersMonth + transferAmount < maxNoTaxMountMaster) {
                    "Комиссия не взимается"
                } else {
                    if (amountOfPreviousTransfersMonth >= maxNoTaxMountMaster)
                    // если платеж сразу идет сверх лимита
                    {
                        "Комиссия " + (transferAmount * taxMaster + additionalCommissionMaster) + " руб"
                    }
                    else
                    // если платеж + предыдущий платеж вместе превышают лимит
                    {
                        val balanceAfterLimit: Int = (amountOfPreviousTransfersMonth + transferAmount) - maxNoTaxMountMaster
                        "Комиссия " + (balanceAfterLimit * taxMaster + additionalCommissionMaster) + " руб"
                    }
                }
            }
        }

        "Visa", "Мир" -> {
            if (maximumInOneTransaction(maximumInOneTransactionCards,transferAmount ))  {
                return maximumInOneTransactionText(maximumInOneTransactionCards)
            }
            return if (maxMountLimitOtherCard(
                    amountOfPreviousTransfersMonth,
                    transferAmount,
                    maxMountLimitOtherCard)) {
                (maxMountLimitOtherCardText(amountOfPreviousTransfersMonth, maxMountLimitOtherCard ))
            } else {
                val commission: Double = transferAmount * taxMountVisa
                if (commission < minCommissionVisa) {
                    "Комиссия $minCommissionVisa руб"
                } else {
                    "Комиссия $commission руб"
                }
            }
        }

        else -> {
            if (maximumInOneTransaction(maximumInOneTransactionVKPay,transferAmount ))  {
                return maximumInOneTransactionText(maximumInOneTransactionVKPay)
            }
            return if (maxMountLimitOtherCard(amountOfPreviousTransfersMonth, transferAmount, maxMountLimitVkPay)) {
                maxMountLimitOtherCardText(amountOfPreviousTransfersMonth, maxMountLimitVkPay)
            } else {
                "Комиссия не взимается"
            }
        }
    }
}

