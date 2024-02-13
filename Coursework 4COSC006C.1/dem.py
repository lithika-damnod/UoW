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

progress_count = 0
trailer_count = 0
retriever_count = 0
exclude_count = 0

while True:
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
    symbol=input("Enter 'y' for yes  or 'q' to quit and view results: ")
    
    
    if symbol=='y':
          continue
    elif symbol=='q':
        break
    else:
        print('You must enter y or q')
        continue

window = GraphWin("Histogram", 600, 550)

title = Text(Point(180, 30), 'Histogram Results')
title.setSize(18)
title.draw(window)

aline = Line(Point(50,450), Point(550,450))
aline.draw(window)
    
       
