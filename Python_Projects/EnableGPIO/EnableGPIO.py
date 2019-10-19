import argparse
import sys
import os

class EnableGPIO:

    GPIO_PATH = '/sys/class/gpio/'
    gpio_number = 0

    def __init__(self, gpio_number):
        self.gpio_number = gpio_number
        print('Enable: gpio{}'.format(self.gpio_number))

    def gpio(self):
        if not os.path.exists(self.GPIO_PATH + 'gpio' + str(self.gpio_number)):
            try:
                file = open(self.GPIO_PATH + 'export', 'w')
                file.write(str(self.gpio_number))
                file.close()
                print('GPIO {} enabled'.format(self.GPIO_PATH + 'gpio' + str(self.gpio_number)))
            except IOError as err:
                print('Can\'t write to file (gpio) {}'.format(err))
        else:
            print('File {} already exists'.format(self.GPIO_PATH + 'gpio' + self.gpio_number))

    def set_direction(self, direction):
        try:
            file = open(self.GPIO_PATH + 'gpio' + str(self.gpio_number) + '/direction', 'w')
            file.write(str(direction))
            file.close()
            print('File {} created'.format(file))
        except IOError as err:
            print('Can\'t set direction {}'.format(err))


def main():
    parser = argparse.ArgumentParser('SET GPIO')
    parser.add_argument('-g', '--gpio', help='Enter gpio number to set', type=str)
    parser.add_argument('-d', '--direction', help='Set gpio direction, in/out', type=str)
    args = parser.parse_args()
    print(args)
    enable_gpio = EnableGPIO(args.gpio)
    enable_gpio.gpio()
    enable_gpio.set_direction(args.direction)


if __name__ == '__main__':
    sys.exit(main())
