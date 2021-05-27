# Program that outputs the factorial of a user inputted 
# non-negative number using subroutines
# @author Gabriel Chai
# @version May 15, 2020
.text 0x00400000
.globl main
main:

la $a0, num # Asks user for the number
li $v0, 4
syscall

li $v0, 5 # Reads user input for the number
syscall

move $a0, $v0

li $v0, 1 #Prints the user input
syscall


subu $sp, $sp 4 # Pushes $ra onto the stack
sw $ra, ($sp)

jal fact # Calculates the factorial

lw $ra, ($sp) # Pops $ra from the stack
addu $sp, $sp, 4

move $t0, $v0

la $a0, out # Prints "! = "
li $v0, 4
syscall

move $a0, $t0 # Prints the factorial
li $v0, 1
syscall

li $v0, 10 # Normal Termination
syscall

# Calculates the factorial of a non-negative number
fact:

beq $a0, 0, zero

subu $sp, $sp 4 # Pushes $ra onto the stack
sw $ra, ($sp)

subu $sp, $sp 4 # Pushes $a0 onto the stack
sw $a0, ($sp)

subu $a0, $a0, 1 # Calculates the factorial of $a0-1

jal fact 

lw $a0, ($sp) # Pops $a0 from the stack
addu $sp, $sp, 4

lw $ra, ($sp) # Pops $ra from the stack
addu $sp, $sp, 4

mulu $v0, $v0, $a0 # Returns $a0 * fact ($a0-1)

jr $ra 

zero:
li $v0, 1 # if $a0==0 returns 1
jr $ra

.data

num: .asciiz "Enter a non negative number: "
out: .asciiz "! = "