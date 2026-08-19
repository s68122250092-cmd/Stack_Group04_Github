## Algorithm A
Input: Infix Expression
1. Tokenize expression
2. Validate expression
3. Create Operator Stack
4. Create Postfix Output
5. For each token:
      If token is Operand:
          Add token to Output
      Else if token is '(':
          Push token to Operator Stack
      Else if token is ')':
          Pop operators until '(' is found
          Remove '('
      Else if token is Operator:
          While top of stack has higher or equal priority:
              Pop operator to Output
          Push current operator
6. Pop remaining operators to Output
7. Create Operand Stack
8. For each token in Postfix:
      If token is Operand:
          Push token
      Else if token is Operator:
          Pop required operands
          Perform operation
          Push result
9. Return top of Operand Stack

## Algorithm B
Input: Infix Expression
1. Tokenize expression
2. Validate expression
3. Create Operand Stack
4. Create Operator Stack
5. For each token:
      If token is Operand:
          Push token to Operand Stack
      Else if token is '(':
          Push token to Operator Stack
      Else if token is ')':
          While top operator is not '(':
              Apply operator
          Remove '('
      Else if token is Operator:
          While top operator has higher or equal priority:
              Apply operator
          Push current operator
6. While Operator Stack is not empty:
      Apply operator
7. Return top of Operand Stack