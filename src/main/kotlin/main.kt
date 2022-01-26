import kotlin.random.Random

fun main() {
    val message = "How many dice do you want to hold or unhold?"
    val hand1 = Hand()
    var rolls = 0
    while (rolls<3) {

        rolls++
        println("$rolls. roll")
        hand1.rollDice()
        println(message)
        val n: Int = Integer.valueOf(readLine())
        hand1.holdDice(n)

        if(rolls == 3) {
            hand1.checkScore()
            rolls = 0
            println("NEW HAND!")
        }
    }

}

class Die(var number: Int = 1, var isLocked: Boolean = false){

    override fun toString(): String {
        return "$number"
    }
}

class Hand(
    private var hand: MutableList<Die> = mutableListOf(Die(), Die(), Die(), Die(), Die(), Die()),
    private var numberOfRolls: Int = 0){

    fun rollDice(){
        var index = 0
        if(numberOfRolls == 3){
            for (die in hand) die.isLocked = false
            numberOfRolls = 0
        }
        for (die in hand){
            index++
            if(!die.isLocked){
                die.number = Random.nextInt(1, 7)
                println("index[$index]->Rolled: ${die.number}")
            }
           else println("index[$index]->Holding: ${die.number}")
        }
        numberOfRolls++
    }

    fun holdDice(n: Int){
        val indexList = mutableListOf<Int>()
        indexList.clear()
        for (i in 0 until n){
            println("Enter index of the ${i+1}. die you want to hold or unhold")
            try {
                indexList.add(Integer.valueOf(readLine())-1)
                hand[indexList[i]].isLocked = !(hand[indexList[i]].isLocked)
            }
           catch (e: IndexOutOfBoundsException){
                println("Enter valid index")
           }

        }

    }

    fun checkScore(){
        val hold = mutableListOf<Int>()
        hold.clear()
        for (die in hand){
            if(die.isLocked) hold.add(die.number)
        }
        if(hold.count() == 4 && hold.distinct().count() == 1) println("You've got P O K E R!")
        else if (hold.count() == 5 && hold.distinct().count() == 1) println("You've got Y A M B!")
        else if(hold.count() == 5 && hold.distinct().count() == 2) println("You've got a F U L L  H O U S E!")
        else if(hold.count() == 5 && hold.distinct().count() == 5 && (sumOfDice(hold) == 15 || sumOfDice(hold) == 20))
            println("You've got a S T R A I G H T!")

        else println("You've got NOTHING :(")
    }
    private fun sumOfDice(hold: MutableList<Int> = mutableListOf()): Int{
        var sum= 0
        for (number in hold) sum += number
        return sum
    }
}