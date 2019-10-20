import argparse
import sys
import time
import os


class SetSignal:

    GPIO = '/sys/class/gpio/gpio'

    def __init__(self, gpio):
        self.GPIO = self.GPIO + str(gpio) + '/value'
        print('send signal to: {}'.format(self.GPIO))

    def check_if_path_exist(self):
        try:
            if not os.path.isfile(self.GPIO):
                print('file {} does\'t exists'.format(self.GPIO))
                sys.exit(0)
        except IOError as err:
            print(err)
            sys.exit(0)

    def send_signal(self, signal):
        x = signal
        while True:
            try:
                if x == 1:
                    file = open(self.GPIO, 'w')
                    file.write(str(x))
                    file.close()
                    print(x)
                    x = 0
                    time.sleep(3)
                else:
                    file = open(self.GPIO, 'w')
                    file.write(str(x))
                    file.close()
                    print(x)
                    x = 1
                    time.sleep(1)
            except OSError as err:
                file = open(self.GPIO, 'w')
                file.write('0')
                file.close()
                print('can not open file', err)
                break
            except KeyboardInterrupt:
                file = open(self.GPIO, 'w')
                file.write('0')
                file.close()
                print('key board interrupt')
                break


def main():
    parser = argparse.ArgumentParser('Set signal on LED')
    parser.add_argument('-g', '--gpio', help='enter gpio', type=int)
    parser.add_argument('-s', '--signal', help='enter initial signal', type=int)
    args = parser.parse_args()
    set_signal = SetSignal(args.gpio)
    set_signal.check_if_path_exist()
    set_signal.send_signal(args.signal)


if __name__ == '__main__':
    sys.exit(main())
