# I declare that my work contains no examples of misconduct, such as plagiarism, or collusion.
# Any code taken from other sources is referenced within my code solution.
# Student ID: 20231990
# Date: 2023/12/10



CREDITS = list(range(0, 121, 20)) # generate possible credit values into a list

from graphics import *



# UNCOMMENT below code to use 'input.txt' as the input
# import sys
# sys.stdin = open('input.txt', 'r')


# stores data required to form the histogram
histogram_data = { 
    'progress': 0, 
    'trailer': 0, 
    'retriever': 0, 
    'excluded': 0, 
}

calculations = [] # stores input progression data  { outcome: "null", credits: [pass, defer, fail] }



def main(): 
    while True:
            # receives credit input
            pass_credits = int(get_valid_input("Enter your total PASS credits: "))
            defer_credits = int(get_valid_input("Enter your total DEFER credits: "))
            fail_credits = int(get_valid_input("Enter your total FAIL credits: "))

            # checks whether the total adds up to 120
            if (pass_credits+defer_credits+fail_credits) != 120: 
                print("Total incorrect. \n")
            else: 
                result = calculate(pass_credits, fail_credits) # pass input values to calculate the answer
                print(result, '\n') # print the result

                # push the results to the progression data array along with the input of credits received
                calculations.append({
                    "outcome": result, 
                    "credits": [pass_credits, defer_credits, fail_credits] 
                })    

                

                print("Would you like to enter another set of data?")
                while True: 
                    proceed_option = input('Enter \'y\' for yes or \'q\' to quit and view results: ')
                    if proceed_option == 'q':  # user wants quit
                        render_histogram() # display the histogram                        
                        print_progression_data() # print the progression data from the list (part 2)
                        save_progression_data() # save the results to a file named 'output.txt' (part 3)
                        print_file_progression_data() # print the content of 'output.txt'  (part 3)

                        return # exit the program
                    elif proceed_option == 'y': # user wants to proceed
                        print('\n')
                        break


# gets input of credit values with validation instead of just using input()
def get_valid_input(prompt): 
    while True: 
        try:
            user_input = int(input(prompt)) # input credit
            if user_input not in CREDITS: # cheks whether the credit value entered is in the CREDITS list
                print("Out of range. \n")
                continue
            
            return user_input

        except ValueError: # catches any incompatible variable type input
            print("Interger required. \n")
        
    
# calculates the progress outcome
def calculate(pass_credits, fail_credits): 
    if fail_credits >= 80: 
        histogram_data["excluded"] += 1  # update histogram data
        return "Exclude"

    if pass_credits == 120: 
        histogram_data["progress"] += 1 # update histogram data
        return "Progress"

    if pass_credits == 100: 
        histogram_data["trailer"] += 1 # update histogram data
        return "Progress (module trailer)"

    histogram_data["retriever"] += 1 # update histogram data
    return "Do not progress – module retriever \n"



# draws the histogram
def render_histogram(): 
    bg_colors = ["#b4e4b3", "#f3d3ad", "#ccd0f2", "#f0b5b5"] # important hex values of bg colors
    min_y_height = 140 
    max_y_height = 500
    y_gap = max_y_height - min_y_height
    last_x_points = [50, 200] # x coordinates that will be used to position bins: this will be updated for each iteration
    n = sum(histogram_data.values()) # adds up the values in the dict 
    max_n = max(histogram_data.values()) # finds the maximum number of occurrences for different progress outcomes 
    fraction =  y_gap / max_n

    window = GraphWin("Histogram", 800, 650)
    window.setBackground("#fbe8e8")

    heading = Text(Point(150, 30), "Histogram Results")
    heading.setSize(30)
    heading.setStyle("bold")
    heading.setTextColor("#cb7f7f")
    heading.draw(window)

    seperator = Line(Point(30, 500), Point(770, 500))
    seperator.draw(window)

    # draws multiple bins according to their values
    for index, (key, value) in enumerate(histogram_data.items()): 
        height = max_y_height - (fraction * value)
        bin = Rectangle(Point(last_x_points[0], height), Point(last_x_points[1], 500))
        bin.setFill(bg_colors[index])

        """
        even if the value is zero, 
        we have to create the Rectangle Component
        since we will need to use methods like .getCenter()
        but we don't draw it because we don't need an empty bin showing up in the histogram for no reason
        """
        if value != 0: 
            bin.draw(window) # draw on the window only if value is not zero

            frequency = Text(Point(bin.getCenter().x, (bin.p1.y - 25)), str(value)) # gets the center x coordinate from the bin so can easily placed in center 
            frequency.setSize(15)
            frequency.setTextColor("#666666")
            frequency.setStyle("bold")
            frequency.draw(window)

        label = Text(Point(bin.getCenter().x, 520), key.capitalize())
        label.setStyle("bold")
        label.setSize(16)
        label.draw(window)

        for index in range(len(last_x_points)): # update the values of current x coordinates (bin), so the next bin can be positioned accurately
            last_x_points[index] += 180
    

    total=Text(Point(120, 500 + 100), f"{n} outcomes in total.") 
    total.setStyle("bold")
    total.setTextColor("#666666")
    total.setSize(17)
    total.draw(window)

    window.getMouse() # wait for user interactions
    window.close() # close the window
    



# prints progression data from the list  (part 2)
def print_progression_data(): 
    print('\nPart 2:')
    for entry in calculations: 
        print(f"{entry['outcome']} - {entry['credits'][0]}, {entry['credits'][1]}, {entry['credits'][2]}")


# saves progression data into a file 'output.txt'  (part 3)
def save_progression_data():
    with open('output.txt', 'w') as f: 
        content = [f"{entry['outcome']} - {entry['credits'][0]}, {entry['credits'][1]}, {entry['credits'][2]} \n" for entry in calculations]
        f.writelines(content)


# prints progression data using the file created  (part 3)
def print_file_progression_data(): 
    f = open('output.txt', 'r') 
    print("\nPart 3:", f.read(), sep='\n')







if __name__ == "__main__":
    main()


# e935a372151f4ee092dc6f54a4a6c40a4d254f9e
