CREDITS = list(range(0, 121, 20))

from graphics import *

histogram_data = { 
    'progress': 0, 
    'trailer': 0, 
    'retriever': 0, 
    'excluded': 0, 
}

calculations = [] # stores

def main(): 
    while True:
            pass_credits = int(get_valid_input("Enter your total PASS credits: "))
            defer_credits = int(get_valid_input("Enter your total DEFER credits: "))
            fail_credits = int(get_valid_input("Enter your total FAIL credits: "))

            if (pass_credits+defer_credits+fail_credits) != 120: 
                print("Total incorrect. \n")
            else: 
                result = calculate(pass_credits, fail_credits)

                print(result, '\n')
                
                print("Would you like to enter another set of data?")
                while True: 
                    proceed_option = input('Enter \'y\' for yes or \'q\' to quit and view results: ')
                    if proceed_option == 'q': 
                        # display the histogram                        
                        render_histogram()
                        return
                    elif proceed_option == 'y': 
                        print('\n')
                        break


def get_valid_input(prompt): 
    while True: 
        try:
            user_input = int(input(prompt))
            if user_input not in CREDITS: 
                print("Out of range. \n")
                continue
            
            return user_input

        except ValueError: 
            print("Interger required. \n")
        
    

def calculate(pass_credits, fail_credits): 
    if fail_credits >= 80: 
        histogram_data["excluded"] += 1
        return "Exclude"

    if pass_credits == 120: 
        histogram_data["progress"] += 1
        return "Progress"

    if pass_credits == 100: 
        histogram_data["trailer"] += 1
        return "Progress (module trailer)"

    histogram_data["retriever"] += 1
    return "Do not progress – module retriever \n"



def render_histogram(): 
    bg_colors = ["#b4e4b3", "#f3d3ad", "#ccd0f2", "#f0b5b5"]
    min_height = 140
    last_x_points = [50, 200] 
    n = sum(histogram_data.values())
    max_n = max(histogram_data.values())

    window = GraphWin("Histogram", 800, 650)
    window.setBackground("#fbe8e8")

    heading = Text(Point(150, 30), "Histogram Results")
    heading.setSize(30)
    heading.setStyle("bold")
    heading.setTextColor("#cb7f7f")
    heading.draw(window)

    seperator = Line(Point(30, 500), Point(770, 500))
    seperator.draw(window)
    for index, (key, value) in enumerate(histogram_data.items()): 
        height = (min_height-(min_height/max_n * value)) + min_height
        bin = Rectangle(Point(last_x_points[0], height), Point(last_x_points[1], 500))
        bin.setFill(bg_colors[index])
        
        if value != 0: 
            bin.draw(window) # draw on the window only if value is not zero

            frequency = Text(Point(bin.getCenter().x, (bin.p1.y - 25)), str(value))
            frequency.setSize(15)
            frequency.setTextColor("#666666")
            frequency.setStyle("bold")
            frequency.draw(window)

        label = Text(Point(bin.getCenter().x, 520), key.capitalize())
        label.setStyle("bold")
        label.setSize(16)
        label.draw(window)

        for index in range(len(last_x_points)): 
            last_x_points[index] += 180
    

    total=Text(Point(120, 500 + 100), f"{n} outcomes in total.") 
    total.setStyle("bold")
    total.setTextColor("#666666")
    total.setSize(17)
    total.draw(window)

    window.getMouse()
    window.close()
    



if __name__ == "__main__":
    main()









# e935a372151f4ee092dc6f54a4a6c40a4d254f9e