from graphics import *

print("This is the progress viewer")

valid_values = [0, 20, 40, 60, 80, 100, 120]

count1 = 0
count2 = 0
count3 = 0
count4 = 0

progression_data=[]

def get_credits_input(prompt):
    while True: 
        try: 
            input_credit = int(input(prompt))
            if input_credit not in valid_values: 
                print('Your input is invalid. Please enter values from [0, 20, 40, 60, 80, 100, 120].')
            else: 
                return input_credit
        except ValueError: 
            print('Invalid input. Please enter integer values.')



def calculate_progress_level(pas, defer, fail): 
    if pas == 120:
        print("Progress")
        progression_data.append([pas,defer,fail,"Progress"])
        count1 += 1
    elif (pas == 100 and defer == 20) or (pas == 100 and fail == 20):
        print('Progress (module trailer)')
        progression_data.append([pas,defer,fail,"Progress (module trailer)"])
        count2 += 1
    elif (pas == 40 and fail == 80) or (pas == 20 and defer == 20 and fail == 80) or (
            pas == 20 and fail == 100) or (defer == 40 and fail == 80) or (
            defer == 20 and fail == 100) or (fail == 120):
        print('Exclude')
        progression_data.append([pas,defer,fail,"exclude"])
        count3 += 1
    else:
        print('Do not progress – module retriever')
        progression_data.append([pas,defer,fail,"Do not progress – module retriever"])
        count4 += 1


# ORDER OF HOW THE CODE RUNS: MAIN
def main():
    terminate = False
    while not terminate:
        pas = get_credits_input("Enter the first value: ")
        defer = get_credits_input("Enter the second value: ")
        fail = get_credits_input("Enter the third value: ")
        
        total = pas + defer + fail
        if total != 120:
            print(f'Your total ({total}) is incorrect. Total should be 120.')
        else:
            calculate_progress_level(pas, defer, fail)
            while True:
                repeat = input('Enter choice, yes or no: ').lower()
                if repeat == 'y':
                    print("You entered yes")
                    break  # Break the inner loop to ask for values again
                elif repeat == 'q':
                    print("Exiting the loop. Goodbye!")
                    terminate = True
                    break  # Exit the program if 'q' is entered
                else:
                    print("Invalid input. Please enter 'y' or 'q'.")

    
    # TODO: do other stuff like showing the histogram after this....
    show_histogram()
    part_2()
    part_3()





def show_histogram():
    # Calculate heights for the histogram bars
    maximum_outcome = max(count1, count2, count3, count4)

    maximum_height = 350

    unit_height=maximum_height/maximum_outcome



    # Drawing the histogram
    Window = GraphWin("Histogram", 800, 600)

    Label = Text(Point(400, 30), "Histogram Result")
    Label.setSize(25)
    Label.draw(Window)

    line = Line(Point(50, 480), Point(750, 480))
    line.draw(Window)

    rectangle1 = Rectangle(Point(100, 480), Point(200, 480 - (unit_height*count1)))
    rectangle1.setFill("#AEF8A1")
    rectangle1.draw(Window)

    rectangle2 = Rectangle(Point(250, 480), Point(350, 480 - (unit_height*count2)))
    rectangle2.setFill("maroon")
    rectangle2.draw(Window)

    rectangle3 = Rectangle(Point(400, 480), Point(500, 480 - (unit_height*count3)))
    rectangle3.setFill("orange")
    rectangle3.draw(Window)

    rectangle4 = Rectangle(Point(550, 480), Point(650, 480 - (unit_height*count4)))
    rectangle4.setFill("yellow")
    rectangle4.draw(Window)

    Label_1 = Text(Point(150,465-(unit_height*count1)),count1)
    Label_1.setSize(25)
    Label_1.draw(Window)

    Label_2 = Text(Point(300,465-(unit_height*count2)),count2)
    Label_2.setSize(25)
    Label_2.draw(Window)

    Label_3 = Text(Point(450,465-(unit_height*count3)),count3)
    Label_3.setSize(25)
    Label_3.draw(Window)

    Label = Text(Point(600,465-(unit_height*count4)),count4)
    Label.setSize(25)
    Label.draw(Window)


    label_4 = Text(Point(150, 520), "Progress")
    label_4.setSize(12)
    label_4.draw(Window)

    label_5 = Text(Point(300, 520), "Trailer")
    label_5.setSize(12)
    label_5.draw(Window)

    label_6 = Text(Point(450, 520), "Exclude")
    label_6.setSize(12)
    label_6.draw(Window)

    label_7 = Text(Point(600, 520), "Retriever")
    label_7.setSize(12)
    label_7.draw(Window)

    Outcomes_in_Total=count1+count2+count3+count4

    Total = Text(Point(400, 550),f"{Outcomes_in_Total}Outcomes in Total")
    Total.setSize(18)
    Total.draw(Window)
    

    # Wait for a mouse click before closing the window
    Window.getMouse()
    Window.close()



def part_2():
    # Display Part 2
    print("Part 2:")
    # Print the stored progression data in the required format
    for data in progression_data:
        print(f"{data[3]} - {data[0]}, {data[1]}, {data[2]}")


def part_3():
    #Display Part 3
    print("Part 3:")    
    # Save progression data to a text file
    with open("progression_data.txt", "w") as file:
        for data in progression_data:
            file.write(f"{data[3]} - {data[0]}, {data[1]}, {data[2]}\n")
    with open("progression_data.txt", "r") as file:
        txtfile_content=file.read()
    print(txtfile_content)
        



if __name__ == "__main__":
    main()

