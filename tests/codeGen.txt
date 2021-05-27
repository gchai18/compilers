	# @author Gabriel Chai
	.text
	.globl main
main:
	# Sets $v0 to 2
	li $v0 2

	# Assigns $v0 to varx
	la $t0 varx
	sw $v0 ($t0)

	# Sets $v0 to varx
	la $t0 varx
	lw $v0 ($t0)

	subu $sp $sp 4
	sw $v0 ($sp)

	# Sets $v0 to 1
	li $v0 1

	lw $t0 ($sp)
	addu $sp $sp 4

	# Adds $t0 and $v0
	addu $v0 $t0 $v0

	# Assigns $v0 to vary
	la $t0 vary
	sw $v0 ($t0)

	# Sets $v0 to varx
	la $t0 varx
	lw $v0 ($t0)

	subu $sp $sp 4
	sw $v0 ($sp)

	# Sets $v0 to vary
	la $t0 vary
	lw $v0 ($t0)

	lw $t0 ($sp)
	addu $sp $sp 4

	# Adds $t0 and $v0
	addu $v0 $t0 $v0

	# Assigns $v0 to varx
	la $t0 varx
	sw $v0 ($t0)

	# Sets $v0 to varx
	la $t0 varx
	lw $v0 ($t0)

	subu $sp $sp 4
	sw $v0 ($sp)

	# Sets $v0 to vary
	la $t0 vary
	lw $v0 ($t0)

	lw $t0 ($sp)
	addu $sp $sp 4

	# Multiplies and $v0
	mulu $v0 $t0 $v0

	# Prints $v0
	move $a0 $v0
	li $v0 1
	syscall

	# Prints a new line
	la $a0 newline
	li $v0 4
	syscall

	# Sets $v0 to varx
	la $t0 varx
	lw $v0 ($t0)

	subu $sp $sp 4
	sw $v0 ($sp)

	# Sets $v0 to vary
	la $t0 vary
	lw $v0 ($t0)

	lw $t0 ($sp)
	addu $sp $sp 4

	ble $t0 $v0 endif1
	
	# Sets $v0 to varx
	la $t0 varx
	lw $v0 ($t0)

	# Prints $v0
	move $a0 $v0
	li $v0 1
	syscall

	# Prints a new line
	la $a0 newline
	li $v0 4
	syscall

	# Sets $v0 to vary
	la $t0 vary
	lw $v0 ($t0)

	# Prints $v0
	move $a0 $v0
	li $v0 1
	syscall

	# Prints a new line
	la $a0 newline
	li $v0 4
	syscall

	
endif1:
	# Sets $v0 to 14
	li $v0 14

	subu $sp $sp 4
	sw $v0 ($sp)

	# Sets $v0 to 14
	li $v0 14

	lw $t0 ($sp)
	addu $sp $sp 4

	bne $t0 $v0 endif2
	
	# Sets $v0 to 14
	li $v0 14

	subu $sp $sp 4
	sw $v0 ($sp)

	# Sets $v0 to 14
	li $v0 14

	lw $t0 ($sp)
	addu $sp $sp 4

	beq $t0 $v0 endif3
	
	# Sets $v0 to 3
	li $v0 3

	# Prints $v0
	move $a0 $v0
	li $v0 1
	syscall

	# Prints a new line
	la $a0 newline
	li $v0 4
	syscall

	
endif3:
	# Sets $v0 to 14
	li $v0 14

	subu $sp $sp 4
	sw $v0 ($sp)

	# Sets $v0 to 14
	li $v0 14

	lw $t0 ($sp)
	addu $sp $sp 4

	bgt $t0 $v0 endif4
	
	# Sets $v0 to 4
	li $v0 4

	# Prints $v0
	move $a0 $v0
	li $v0 1
	syscall

	# Prints a new line
	la $a0 newline
	li $v0 4
	syscall

	
endif4:
	
endif2:
	# Sets $v0 to 15
	li $v0 15

	subu $sp $sp 4
	sw $v0 ($sp)

	# Sets $v0 to 14
	li $v0 14

	lw $t0 ($sp)
	addu $sp $sp 4

	ble $t0 $v0 endif5
	
	# Sets $v0 to 5
	li $v0 5

	# Prints $v0
	move $a0 $v0
	li $v0 1
	syscall

	# Prints a new line
	la $a0 newline
	li $v0 4
	syscall

	
endif5:
	# Sets $v0 to 1
	li $v0 1

	# Assigns $v0 to varcount
	la $t0 varcount
	sw $v0 ($t0)

beginwhile6:
	# Sets $v0 to varcount
	la $t0 varcount
	lw $v0 ($t0)

	subu $sp $sp 4
	sw $v0 ($sp)

	# Sets $v0 to 15
	li $v0 15

	lw $t0 ($sp)
	addu $sp $sp 4

	bgt $t0 $v0 endwhile6
	
	# Sets $v0 to varcount
	la $t0 varcount
	lw $v0 ($t0)

	# Prints $v0
	move $a0 $v0
	li $v0 1
	syscall

	# Prints a new line
	la $a0 newline
	li $v0 4
	syscall

	# Sets $v0 to varcount
	la $t0 varcount
	lw $v0 ($t0)

	subu $sp $sp 4
	sw $v0 ($sp)

	# Sets $v0 to 1
	li $v0 1

	lw $t0 ($sp)
	addu $sp $sp 4

	# Adds $t0 and $v0
	addu $v0 $t0 $v0

	# Assigns $v0 to varcount
	la $t0 varcount
	sw $v0 ($t0)

	j beginwhile6

endwhile6:
	li $v0 10
	syscall #halt
	.data
	newline: .asciiz "\n" 
	varx: .word 0
	vary: .word 0
	varcount: .word 0
