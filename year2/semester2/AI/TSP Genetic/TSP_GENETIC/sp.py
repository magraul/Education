def isPalindrome(string):
    for i in range(len(string)//2):
        if string[i] != string[len(string)-i-1]:
            return False
    return True





n = int(input())
l = []
for i in range(n):
    l.append(input())

for line in l:
    rez = ''
    lst = len(line)
    for poz, c in enumerate(line[::-1]):
        if c == c.upper():
            if poz - lst == 2:
                cuv = c
            else:
                cuv = line[len(line)-poz-1:lst]
            cuv = cuv[::-1]
            cuv = cuv.capitalize()
            rez += cuv
            lst = poz-1
    print(rez)






#
#
#
# def div(a, b):
#     n = 0
#     for i in range(1, min(a, b) + 1):
#         if a % i == b % i == 0:
#             n += 1
#     return n
#
# from fractions import gcd
# from functools import reduce
#
# def find_gcd(list):
#     x = reduce(gcd, list)
#     return x
#
# n = int(input())
# vec = input()
# lis = vec.strip().split(" ")
# lista = [int(i) for i in lis]
#
#
# lista_divs = []
# cel = find_gcd([lista[0], lista[1]])
# n = div(lista[0], lista[1])
# lista_divs.append(n)
#
# for i in range(1, len(lista)):
#     find_gcd([cel, lista[i]])
#     n = div(cel, lista[i])
#     lista_divs.append(n)
# print(min(lista_divs))
#
