/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package kalculator;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Stack;

/**
 *
 * @author kumar
 */
public class ExpressionEvaluator {
    int[][] precedence=new int[][]{{0,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1},{0,0,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1},{0,0,0,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1},{0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,1,1,1,1,1,1,1,0},{0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,1,1,1,1,1,1,1,0},{0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,1,1,1,1,1,1,1,0},{0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,1,1,1,1,1,1,1,0},{0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,1,1,1,1,1,1,1,0},{0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,1,1,1,1,1,1,1,0},{0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,1,1,1,1,1,1,1,0},{0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,1,1,1,1,1,1,1,0},{0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,1,1,1,1,1,1,1,0},{0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,1,1,1,1,1,1,1,0},{0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,1,1,1,1,1,1,1,0},{0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,1,1,1,1,1,1,1,0},{0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,1,1,1,1,1,1,1,0},{0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,1,1,1,1,1,1,1,0},{0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,1,1,1,1,1,1,1,0},{0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,1,1,1,1,1,0},{0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,1,1,1,1,1,0},{0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,1,1,1,1,0},{0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,1,0},{0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,1,0},{0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,1,0},{0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0},{0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,1,1,1,1,1,1,1,0}};
    //precedence[a,b]=1 only if operator a is completely greater than b i.e *>+ but not for *</
    HashMap<Character,Integer> operatorHashMap;
    String string;
    Stack<Integer> stack;
    ArrayList<String> postfix;
    String operatorStrings[],errorMessage;
    char hashingChars[];
    int degOrRad;

    public ExpressionEvaluator() {
    
        hashOperators();
        hashStrings();
        //start(string);
    }
    private void hashStrings()
    {
        operatorStrings=new String[]{"asinh","acosh","atanh","sinh","cosh","tanh","asin","acos","atan","sin","cos","tan","e^","Log","log","ln"};
        hashingChars=new char[]{'S','H','T','s','h','t','I','O','A','i','o','a','e','L','l','n'};

    }   
    
    public String start(String string,int degOrRad)
    {try{
        this.degOrRad=degOrRad;
        this.string=string;
        errorMessage="Invalid Expression";
        string=modify(string);
        //System.out.println(string);
        convertToPostfix(string);
        for(int i=0;i<postfix.size();i++)
        {
            //System.out.println(postfix.get(i));
        }
        string=evaluatePostfix();
        stack.clear();
        postfix.clear();
        return string;
     }
    catch(Exception e)
    {
        return errorMessage;
    }
        
    }
    
    private String evaluatePostfix() throws Exception{
        
        Stack<String> operandStack=new Stack<String>();
        String temp;
        Double firstOperand,secOperand;
        for(int i=0;i<postfix.size();i++)
        {
            temp=postfix.get(i);
            if((temp.charAt(0)>='0' && temp.charAt(0)<='9')|| temp.charAt(0)=='.')
            {
                operandStack.push(temp);
            }
            else
            {
                switch(temp.charAt(0))
                {
                    case '+':
                    {
                        secOperand=Double.parseDouble(operandStack.pop());
                        firstOperand=Double.parseDouble(operandStack.pop());
                        operandStack.push(firstOperand+secOperand+"");
                        break;
                    }
                    case '*':
                    {
                        secOperand=Double.parseDouble(operandStack.pop());                        
                        firstOperand=Double.parseDouble(operandStack.pop());
                        operandStack.push(firstOperand*secOperand+"");
                        break;
                    }
                    case '/':
                    {
                        secOperand=Double.parseDouble(operandStack.pop());                        
                        firstOperand=Double.parseDouble(operandStack.pop());
                        operandStack.push(firstOperand/secOperand+"");
                        break;
                    }
                    case '%':
                    {
                        secOperand=Double.parseDouble(operandStack.pop());                        
                        firstOperand=Double.parseDouble(operandStack.pop());
                        operandStack.push(firstOperand%secOperand+"");
                        break;
                    }
                    case '`':
                    {
                        firstOperand=Double.parseDouble(operandStack.pop());
                        operandStack.push(-firstOperand+"");
                        break;
                    }
                    case '!':
                    {
                        int intOperand=Integer.parseInt(operandStack.pop());
                        if(intOperand<0)
                        {
                            errorMessage="Factorial is not valid for negative numbers";
                            throw new Exception();
                        }
                        else
                        operandStack.push(factorial(intOperand)+"");
                        break;
                    }
                    case 'e':
                    {
                        firstOperand=Double.parseDouble(operandStack.pop());
                        operandStack.push(Math.pow(Math.E, firstOperand)+"");
                        break;
                    }
                    case '√':
                    {
                        firstOperand=Double.parseDouble(operandStack.pop());
                        operandStack.push(Math.pow(firstOperand, 1.0/2)+"");
                        break;
                    }
                    case 'n':
                    {
                        firstOperand=Double.parseDouble(operandStack.pop());
                        operandStack.push(Math.log(firstOperand)+"");
                        break;
                    }
                    case 'l':
                    {
                        firstOperand=Double.parseDouble(operandStack.pop());
                        operandStack.push(Math.log10(firstOperand)+"");
                        break;
                    }
                    case 'L':
                    {
                        firstOperand=Double.parseDouble(operandStack.pop());
                        operandStack.push(Math.log(firstOperand)/Math.log(2)+"");
                        break;
                    }
                    case 'i':
                    {
                        firstOperand=Double.parseDouble(operandStack.pop());
                        operandStack.push(Math.sin(makeItRadians(firstOperand))+"");
                        break;
                    }
                    case 'o':
                    {
                        firstOperand=Double.parseDouble(operandStack.pop());
                        operandStack.push(Math.cos(makeItRadians(firstOperand))+"");
                        break;
                    }
                    case 'a':
                    {
                        firstOperand=Double.parseDouble(operandStack.pop());
                        operandStack.push(Math.tan(makeItRadians(firstOperand))+"");
                        break;
                    }
                    case 'I':
                    {
                        firstOperand=Double.parseDouble(operandStack.pop());
                        operandStack.push(Math.asin(firstOperand)+"");
                        break;
                    }
                    case 'O':
                    {
                        firstOperand=Double.parseDouble(operandStack.pop());
                        operandStack.push(Math.acos(firstOperand)+"");
                        break;
                    }
                    case 'A':
                    {
                        firstOperand=Double.parseDouble(operandStack.pop());
                        operandStack.push(Math.atan(firstOperand)+"");
                        break;
                    }
                    case 's':
                    {
                        firstOperand=Double.parseDouble(operandStack.pop());
                        operandStack.push(Math.sinh(firstOperand)+"");
                        break;
                    }
                    case 'h':
                    {
                        firstOperand=Double.parseDouble(operandStack.pop());
                        operandStack.push(Math.cosh(firstOperand)+"");
                        break;
                    }
                    case 't':
                    {
                        firstOperand=Double.parseDouble(operandStack.pop());
                        operandStack.push(Math.tanh(firstOperand)+"");
                        break;
                    }
                    case 'S':   //Not Yet Implemented
                    {
                        errorMessage="Inverse hyperbolic functions are disabled";
                        throw new Exception();
                        //break;
                    }
                    case 'H':   //Not Yet Implemented
                    {
                        errorMessage="Inverse hyperbolic functions are disabled";
                        throw new Exception();
                        //break;
                    }
                    case 'T'://Not Yet Implemented
                    {
                        errorMessage="Inverse hyperbolic functions are disabled";
                        throw new Exception();
                        //break;
                    }
                    case 'P':
                    {
                        int r=Integer.parseInt(operandStack.pop()),n;                        
                        n=Integer.parseInt(operandStack.pop());
                        //System.out.println(factorial(n)+""+factorial(r));
                        operandStack.push(factorial(n)/factorial(n-r)+"");
                        break;
                    }
                    case 'C':
                    {
                        int r=Integer.parseInt(operandStack.pop()),n;                        
                        n=Integer.parseInt(operandStack.pop());
                        long value=1;
                        for(int j=0;j<r;i++)
                        {
                            value=value*n/r;
                            n--;r--;
                        }
                        operandStack.push(value+"");
                        break;
                    }
                    case '^':
                    {
                        secOperand=Double.parseDouble(operandStack.pop());                        
                        firstOperand=Double.parseDouble(operandStack.pop());
                        operandStack.push(Math.pow(firstOperand, secOperand)+"");
                        break;
                    }
                    
                    
                }
            }
        }
        //System.out.println(operandStack.peek());
        return operandStack.peek();
        
    }
    
    private void convertToPostfix(String string) {
       
       stack=new Stack<Integer>();
       postfix=new ArrayList<>(); 
       for(int i=0;i<string.length();i++)
       {
           if(string.charAt(i)=='(')
           {
               stack.push((int)'(');
           }
           else if((string.charAt(i)>='0' && string.charAt(i)<='9')|| string.charAt(i)=='.')
           {
               int j=i;
               i++;
               for(;i<string.length();i++)
               {
                   if((string.charAt(i)>='0' && string.charAt(i)<='9')|| string.charAt(i)=='.')
                   {
                       continue;
                   }
                   else break;
               }
               postfix.add(string.substring(j,i));
               i--;
           }
           else if(string.charAt(i)==')')
           {
               int c;
               while((c=stack.pop())!='(')
               {
                   postfix.add(""+(char)c);
               }
 
           }
           else
           {
               while(stack.size()>0 && stack.peek()!='(' && precedence[operatorHashMap.get(string.charAt(i))][operatorHashMap.get((char)(int)stack.peek())]==0)
               {
                   postfix.add(""+(char)(int)stack.pop());
               }
               stack.push((int)string.charAt(i));
           }
       }
       while(stack.size()>0)
       {
           postfix.add(""+(char)(int)stack.pop());
       }
        
    }
    public void hashOperators()
    {
        operatorHashMap=new HashMap<Character,Integer>();
        char operators[]=new char[]{'!','`','e','n','l','L','i','o','a','I','O','A','s','h','t','S','H','T','P','C','^','/','*','%','+','√'};
        for(int i=0;i<operators.length;i++)
        {
            operatorHashMap.put(operators[i], i);                    
        }
    }
    
    public static void main(String args[])
    {
        new ExpressionEvaluator();
    }
    
    
    
    
    
    
    
    private String modify(String s) {
        if(s.charAt(0)=='-')		//first change all unary functions into uni letter operators
        {
            s="`"+s.substring(1);
        }
        
        for(int i=1;i<s.length();i++)
        {
            if(s.charAt(i)=='-' &&s.charAt(i-1)!=')' &&(s.charAt(i-1)<'0' || s.charAt(i-1)>'9'))
            {
                s=s.substring(0,i)+"`"+s.substring(i+1);
                //System.out.println(')');
            }
            else if(s.charAt(i)=='-')
            {
                s=s.substring(0,i)+"+`"+s.substring(i+1);
            }
            if(s.charAt(i-1)=='e' && s.charAt(i)=='^')//for e^
            {
                s=s.substring(0, i)+s.substring(i+1);
            }
        }
        //System.out.println(s);
        for(int i=0;i<operatorStrings.length;i++)
        {
            s=s.replaceAll(operatorStrings[i], ""+hashingChars[i]);
        }
        //System.out.println(s);

        return s;
    }
    
    
    //Supporting functions
    public static long factorial(int n) {
		if (n<=1)
			return 1;
		else
			return(n*factorial(n-1));
	
    }
    public double makeItRadians(double val)
    {
        if(degOrRad==0)
        {
            val=Math.PI*val/180;
        }
        return val;
        
    }
    
}
