# Bruteforce
A bruteforce password cracker written in Java, converted from svn repo with svn2git. It's only partially complete due to time constraints. 

It takes the following arguments via command line or interactive mode (-i).

* -bf to crack a password with bruteforce
    * -zip crack encrypted zip file
        * filename, password pattern, dictionary source or wordlist (optional) 
    * -hash crack hashed password
        * hash, password pattern, dictionary source (optional) 
    * -cmd crack a command line service/program
        * cmd to execute including a placeholder for password, password pattern, dictionary source or wordlist (optional) 
* -gen to generate passwords of given entropy
    * entropy
    * password pattern 
* -en to calculate entropy of given password
    * password 
* -scp to scrape source to a dictionary
    * dictionary source 

The password pattern determines the character set and length of candidate passwords to generate according to the following rules:

    Character set selectors
        a small letters
        A big letters
        n digits
        s special characters
        + concatenate patterns
        [a-c] range of characters from a to c, i.e. characters 'a', 'b', 'c' in this order
        [a--c] characters 'a', '-', 'c' in this order
        {a-c} range from a character to c character to generate permutations from
        {a--c} character set 'a', '-', 'c' to generate permutations from
        {[abc][ABC][qwerty]} permuted character sets in original order 
    Dictionary words
        d dictionary words 
    Length selectors
        n length n of preceding pattern
        n-m length between n and m of preceding pattern
        n,m,o lengths n,m,o of preceding pattern 

Examples:

    a3,5,7n2 - 3,5 or 7 small letters followed by 2 digits
    da+A+n+s2 - a dictionary word followed by a 2 character pattern generated from small and big letters, numbers and special characters
    d3-6[LOL]n2 - a dictionary word of length 3 to 6 characters, characters 'LOL' and 2 digits
    {a-cgk-n[12][45][87]}8[98] - an 8 character/sequence pattern generated from characters/sequences 'a','b','c','g','k','l','m','n','12','45','87' followed by characters 98 
