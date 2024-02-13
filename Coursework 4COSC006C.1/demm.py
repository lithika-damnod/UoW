from graphics import *

def get_credits(valid):
    while True:
        try:
            credits=int(input(valid))
            if credits not in range(0,121,20):
                        print('Out of range')
                        continue
            return credits
        except ValueError:
            print('Integer required')

def draw_rectangle(startx, starty, endx, endy, colour):
    box = Rectangle(Point(startx, starty), Point(endx, endy))
    box.setFill(colour)
    box.draw(window)

def texts(start, end, text, size):
    title = Text(Point(start, end), text)
    title.setSize(size)
    title.draw(window)

progress_count = 0
trailer_count = 0
retriever_count = 0
exclude_count = 0
symbol = ''

while symbol != 'q':
    pass_credit = get_credits('Please enter your credits at pass: ')
    defer_credit = get_credits('Please enter your credits at defer: ')
    fail_credit = get_credits('Please enter your credit at fail: ')
             
    if pass_credit+defer_credit+fail_credit!=120:
        print('Total incorrect')
        continue
    else:
        if pass_credit==120:
            grade='progress'
            progress_count += 1
        elif pass_credit==100:
            grade='Progress (module trailer)'
            trailer_count += 1
        elif fail_credit>=80:
            grade='Exclude'
            exclude_count += 1
        else:
            grade='Do not Progress - module retriever'
            retriever_count += 1
                
    print(grade)
    print('Would you life to enter another data?')

    # ISSUE 1: The below prompt breaks if an unrecognized character is entered

    """
    symbol=input("Enter 'y' for yes  or 'q' to quit and view results: ")
    
    
    if symbol=='y':
          continue
    elif symbol=='q':
        break
    else:
        print('You must enter y or q')
        continue

    """

    while True: 
        symbol=input("Enter 'y' for yes  or 'q' to quit and view results: ")
        if symbol == 'y': 
            break 
        if symbol == 'q': break



window = GraphWin("Histogram", 600, 550)

# ISSUE 2: You forgot assign the background color!
window.setBackground("white")

texts(180, 30, 'Histogram Results', 18)

aline = Line(Point(50,450), Point(550,450))
aline.draw(window)

texts(120, 460, 'Progress', 12)
texts(240, 460, 'Retriever', 12)
texts(360, 460, 'Trailer', 12)
texts(480, 460, 'Excluded', 12)

max_height = 350
max_count = max(progress_count, trailer_count, retriever_count, exclude_count)
total_count = progress_count + trailer_count + retriever_count + exclude_count

h1 = (max_height/max_count) * progress_count
h2 = (max_height/max_count) * trailer_count
h3 = (max_height/max_count) * retriever_count
h4 = (max_height/max_count) * exclude_count

texts(120, 440-h1, progress_count, 12)
texts(240, 440-h2, trailer_count, 12)
texts(360, 440-h3, retriever_count, 12)
texts(480, 440-h4, exclude_count, 12)

draw_rectangle(80, 450, 160, 450-h1, 'green')
draw_rectangle(200, 450, 280, 450-h2, 'lime')
draw_rectangle(320, 450, 400, 450-h3, 'cyan')
draw_rectangle(440, 450, 520, 450-h4, 'red')

texts(120, 460, 'Progress', 12)
texts(240, 460, 'Retriever', 12)
texts(360, 460, 'Trailer', 12)
texts(480, 460, 'Excluded', 12)

texts(180, 490, f'{total_count} outcomes in total', 16)

# ISSUE 3: You should be closing down the window created after some interactions
window.getMouse()
window.close()


# TODO: Now you just want to add code here for part b and c that's all 