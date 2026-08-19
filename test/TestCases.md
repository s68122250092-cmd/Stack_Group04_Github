# Test Case Table

| ID | Input | Expected | Case |
|---|---|---|---|
| TC01 | `3 + 4 * 2` | `11` | Normal / Priority |
| TC02 | `(3 + 4) * 2` | `14` | Parentheses |
| TC03 | `((8 + 2) * 5)` | `50` | Nested parentheses |
| TC04 | `(3 + 4` | Error: วงเล็บไม่สมดุล | Boundary/Error |
| TC05 | `3 + 4)` | Error: วงเล็บปิดเกิน | Boundary/Error |
| TC06 | `3 + * 4` | Error: Operator อยู่ผิดตำแหน่ง | Validation |
| TC07 | `10 / (5 - 5)` | Error: หารด้วยศูนย์ไม่ได้ | Arithmetic Error |
| TC08 | empty | Error: นิพจน์ว่าง | Boundary |
| TC09 | `100 / 5 / 2` | `10` | Left associativity |
| TC10 | `12 + 34` | `46` | Multi-digit operand |
| TC11 | `3 & 4` | Error: อักขระไม่รองรับ | Invalid token |
