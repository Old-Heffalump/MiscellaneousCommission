fun main(args: Array<String>) {
    println(translations(12000, 10000, "Visa"))
    println(translations(590_000, 11000, "Мир"))
    println(translations(29_000, 12000, "VkPay"))
    println(translations(59_000, 18000, "Mastercard"))


}

fun maxMountLimitOtherCard(
    amountOfPreviousTransfersMonth: Int,
    transferAmount: Int,
    maxMountLimitOtherCard: Int
): Boolean {
    return maxMountLimitOtherCard < (amountOfPreviousTransfersMonth + transferAmount)
}

fun maxMountLimitOtherCardText(amountOfPreviousTransfersMonth: Int, maxMountLimitOtherCard: Int): String {
    return "Лимит транзакция превышен! Остаток для переводов в месяц составляет " + (maxMountLimitOtherCard - amountOfPreviousTransfersMonth) + " руб."
}


fun translations(amountOfPreviousTransfersMonth: Int = 0, transferAmount: Int, cardType: String = "VkPay"): String {
    val maxNoTaxMountMaster: Int = 75_000
    val taxMaster: Double = 0.006
    val taxMountVisa: Double = 0.0075
    val minCommissionVisa: Int = 35
    val maxMountLimitVkPay = 40_000
    val maxMountLimitOtherCard = 600_000
    val additionalCommissionMaster = 20
    when (cardType) {
        "Mastercard", "Maestro" -> {
            return if (maxMountLimitOtherCard( // Условие превышения лимита месячных транзакций
                    amountOfPreviousTransfersMonth,
                    transferAmount,
                    maxMountLimitOtherCard
                )
            ) {
                (maxMountLimitOtherCardText(  // текст который должен вернутся
                    amountOfPreviousTransfersMonth,
                    maxMountLimitOtherCard
                ))

            } else {
                if (amountOfPreviousTransfersMonth + transferAmount < maxNoTaxMountMaster) {
                    "Комиссия не взимается"
                } else {
                    val balanceAfterLimit: Int = (amountOfPreviousTransfersMonth + transferAmount) - maxNoTaxMountMaster
                    "Комиссия " + (balanceAfterLimit * taxMaster + additionalCommissionMaster) + " руб"
                }
            }
        }

        "Visa", "Мир" -> {
            return if (maxMountLimitOtherCard(amountOfPreviousTransfersMonth, transferAmount, maxMountLimitOtherCard)) {
                (maxMountLimitOtherCardText(amountOfPreviousTransfersMonth, maxMountLimitOtherCard))
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
            return if (maxMountLimitOtherCard(amountOfPreviousTransfersMonth, transferAmount, maxMountLimitVkPay)) {
                maxMountLimitOtherCardText(amountOfPreviousTransfersMonth, maxMountLimitVkPay)
            } else {
                "Комиссия не взимается"
            }
        }
    }
}

