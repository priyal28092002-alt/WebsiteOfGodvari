package com.example.Patterns;

public class P2 {
	public static void main(String[] args) {
		for(int r=1; r<=5; r++) //rows
		{
			for(int c=1; c<=5; c++) //cols
			{
				if(r==1 || r==5 || c==1 || c==5) {
					System.out.print("* ");
				}else {
					System.out.print("  ");
				}
			}
			System.out.println();
		}
	}
}
