# Program that takes in the user input for two numbers and 
# outputs the maximum using a subroutine
# @author Gabriel Chai
# @version May 15, 2020
.text 0x00400000
.globl main
main:

la $a0, first # Asks user for the first number
li $v0, 4
syscall

li $v0, 5 # Reads user input for the first number
syscall

move $t0, $v0

la $a0, second # Asks user for the second number
li $v0, 4
syscall

li $v0, 5 # Readers user input for the second number
syscall

move $t1, $v0

la $a0, max # Prints "Maximum: "
li $v0, 4
syscall

move $a0, $t0
move $a1, $t1

subu $sp, $sp 4 # Pushes $ra onto the stack
sw $ra, ($sp)

jal max2 # Calculates the maximum

lw $ra, ($sp) # Pops $ra from the stack
addu $sp, $sp, 4

move $a0, $v0 # Prints the maximum of the 2 numbers
li $v0, 1
syscall

li $v0, 10 # Normal Termination
syscall

# Calculates the maximum of 2 numbers
max2:

bgt $a0, $a1, greater
move $v0 $a1 # if $a0 <= $a1 returns $a1
jr $ra

greater:
move $v0 $a0 # if $a0 > $a1 returns $a0
jr $ra

.data

first: .asciiz "First Number: "
second: .asciiz "Second Number: "
max: .asciiz "Maximum: "