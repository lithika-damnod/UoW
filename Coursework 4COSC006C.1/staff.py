#Student ID : 20231949
#Date : 


#Checking for invalid inputs
def check_credits(prompt):
    while True:
        try:
            credits_input = int(input(prompt))
            if credits_input in [0, 20, 40, 60, 80, 100, 120]:
                print("Out of range.")
            else: 
                return credits_input
        except ValueError:
            print("Integer required")

        
#To get the progress outcome. 
def progression_outcome(credits_for_pass, credits_for_fail):
    if credits_for_pass == 120: #The output for all the input combinations where the pass credit is 120
        return ("Progress")
    elif credits_for_pass == 100: #The output for all the input combinations where the pass credit is 100
        return ("Progress (module trailer)")
    elif credits_for_fail >= 80: #The output for all the input combinations where the fail credit if greater than or equals to 80
        return ("Exclude")
    else:  #The output for all the other entries.
        return ( "Do no progress - module retriever")


#The main function where all the other functions are called.
def main ():
    data_list= []

    while True:
        
    #Taking the entries from the users and validating to be in the range using credits_range
        credits_for_pass = check_credits("Enter your credits at pass : ")
        credits_for_defer = check_credits("Enter your credits at defer : ")
        credits_for_fail = check_credits("Enter your credits at fail : ")

        #validating the set of data to be in the correct total ; 120
        total_credits = credits_for_pass + credits_for_defer + credits_for_fail

        if total_credits != 120:
            print("Total incorrect.")
        else:
            outcome = progression_outcome(credits_for_pass, credits_for_defer, credits_for_fail)
            data_list.append([outcome, credits_for_pass, credits_for_defer, credits_for_fail])
        
        #last prompt to iterate the programe or exit with the output of the histogram
        print("Would you like to enter another set of data?")
        decision = input("Enter 'y' for yes or 'q' to quit and view results : ")

        if decision.lower() == 'q':
            for data in data_list:
                print(f"{data[0]} - {data[1]}, {data[2]}, {data[3]}")
            break
        elif decision.lower() != 'y':
            break
            
            

main()
    

























    
