import random
import string


def randomword(length):
    """
    functe care genereaza un cuvant random cu caractere avand prima litera mare
    :param length: lungimea cuvantului
    :return:
    """
    letters = string.ascii_lowercase
    first = string.ascii_uppercase
    return random.choice(first) + ''.join(random.choice(letters) for i in range(length))


