Yavuzalp Turkoglu, Jonathan Cho

1-) I think we have done a good job over all but we faced one promblem in Java one. when we start regex with ^ and end with $, it works if we put proper input at first try, 
but if we enter invalid input at first, we cant get pattertn to match any input anymore. It always gives problem, so we removed ^,$ but which lets user put spaces.

2-) For name and last name, we covered overflow case by putting upper limit of 50 as well as not allowing spaces.
    For integer inputs we made sure that user enters only integers.
    For the calculations with integers, we covered integer overflow cases.
    For getting file names, we made sure user can only enter name with .txt extension and can't put spaces in the name so they dont try to run some sort of script, and we put upper limit of 100.
    For passwords, we allow alphanumeric and special characters, up to 50 characters and no spaces again. We noticed that with large number of characters the hashing is not working right. 
