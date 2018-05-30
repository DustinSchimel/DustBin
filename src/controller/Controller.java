package controller;

import view.Display;

import java.awt.*;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.*;

public class Controller
{
    public static final int EMPTY = 0;
    public static final int SAND = 1;
    public static final int METAL = 2;
    public static final int WATER = 3;
    public static final int SMOKE = 4;
    public static final int DIRT = 5;
    public static final int WOOD = 6;
    public static final int ACID = 7;
    public static final int PINK_VIRUS = 8;
    public static final int PURPLE_VIRUS = 9;
    public static final int FIRE = 10;
    public static final int OBSIDIAN = 11;
    public static final int BOMB = 12;
    public static final int EXPLOSION_FIRE = 13;

    private int[][] grid;
    private Display display;

    public Controller()
    {
        int numRows = 75;
        int numCols = 100;

        String[] toolNames;
        toolNames = new String[13];
        //toolNames = new String[14];
        toolNames[EMPTY] = "Eraser";
        toolNames[SAND] = "Sand";
        toolNames[METAL] = "Metal";
        toolNames[WATER] = "Water";
        toolNames[SMOKE] = "Smoke";
        toolNames[DIRT] = "Dirt";
        toolNames[WOOD] = "Wood";
        toolNames[ACID] = "Acid";
        toolNames[PINK_VIRUS] = "Pink Virus";
        toolNames[PURPLE_VIRUS] = "Purple Virus";
        toolNames[FIRE] = "Fire";
        toolNames[OBSIDIAN] = "Obsidian";
        toolNames[BOMB] = "Bomb";
        //toolNames[EXPLOSION_FIRE] = "Explosion Fire";

        grid = new int[numRows][numCols];

        display = new Display("SandGame", numRows, numCols, toolNames, this);
    }

    //Called when the user clicks on a location using the given tool
    private void locationClicked(int row, int col, int tool)
    {
        //Assign the values associated with the parameters to the grid
        grid[row][col] = tool;
    }

    public void updateDisplay()
    {
        for (int row = 0; row < grid.length; row++)
        {
            for (int col = 0; col < grid[0].length; col++)
            {
                if (grid[row][col] == EMPTY)
                {
                    display.setColor(row, col, Color.BLACK);
                }
                else if (grid[row][col] == SAND)
                {
                    display.setColor(row, col, new Color(193, 154, 107));
                }
                else if (grid[row][col] == METAL)
                {
                    display.setColor(row, col, Color.LIGHT_GRAY);
                }
                else if (grid[row][col] == WATER)
                {
                    display.setColor(row, col, Color.BLUE);
                }
                else if (grid[row][col] == SMOKE)
                {
                    display.setColor(row, col, new Color(73, 73, 73));
                }
                else if (grid[row][col] == DIRT)
                {
                    display.setColor(row, col, new Color(87, 59, 12));
                }
                else if (grid[row][col] == WOOD)
                {
                    display.setColor(row, col, new Color(130, 82, 1));
                }
                else if (grid[row][col] == ACID)
                {
                    display.setColor(row, col, new Color(137, 255, 0));
                }
                else if (grid[row][col] == PINK_VIRUS)
                {
                    display.setColor(row, col, new Color(255, 0, 255));
                }
                else if (grid[row][col] == PURPLE_VIRUS)
                {
                    display.setColor(row, col, new Color(138, 43, 226));
                }
                else if (grid[row][col] == FIRE)
                {
                    int randomColor = (int) (Math.random() * 3);

                    if (randomColor == 0)
                    {
                        display.setColor(row, col, new Color(253,207,88));
                    }
                    else if (randomColor == 1)
                    {
                        display.setColor(row, col, new Color(242,125,12));
                    }
                    else if (randomColor == 2)
                    {
                        display.setColor(row, col, new Color(128,9,9));
                    }
                }
                else if (grid[row][col] == OBSIDIAN)
                {
                    display.setColor(row, col, new Color(38, 40, 41));
                }
                else if (grid[row][col] == BOMB)
                {
                    display.setColor(row, col, new Color(75, 83, 32));
                }
                else if (grid[row][col] == EXPLOSION_FIRE)
                {
                    int randomColor = (int) (Math.random() * 3);

                    if (randomColor == 0)
                    {
                        display.setColor(row, col, new Color(253,207,88));
                    }
                    else if (randomColor == 1)
                    {
                        display.setColor(row, col, new Color(242,125,12));
                    }
                    else if (randomColor == 2)
                    {
                        display.setColor(row, col, new Color(128,9,9));
                    }
                }
            }
        }
    }

    //Called repeatedly.
    public void updatePixels()
    {
        int randomRow = (int) (Math.random() * grid.length);
        int randomCol = (int) (Math.random() * grid[0].length);

        if (randomRow != grid.length - 1)	//If the row is not the bottom row, objects with gravity
        {
            if (grid[randomRow][randomCol] == SAND)
            {
                if (grid[randomRow + 1][randomCol] == EMPTY)
                {
                    grid[randomRow][randomCol] = EMPTY;
                    grid[randomRow + 1][randomCol] = SAND;
                }
                if (grid[randomRow + 1][randomCol] == WATER)
                {
                    grid[randomRow][randomCol] = WATER;
                    grid[randomRow + 1][randomCol] = SAND;
                }
                if (grid[randomRow + 1][randomCol] == SMOKE)
                {
                    grid[randomRow][randomCol] = SMOKE;
                    grid[randomRow + 1][randomCol] = SAND;
                }
                if (grid[randomRow + 1][randomCol] == ACID)
                {
                    int fallthroughChance  = (int) (Math.random() * 2);

                    if (fallthroughChance == 0)
                    {
                        grid[randomRow][randomCol] = ACID;
                        grid[randomRow + 1][randomCol] = SAND;
                    }
                    else
                    {
                        grid[randomRow][randomCol] = EMPTY;
                        grid[randomRow + 1][randomCol] = EMPTY;
                    }
                }
                if (grid[randomRow + 1][randomCol] == FIRE)
                {
                    int smokeChance = (int) (Math.random() * 8);

                    if (smokeChance == 0)
                    {
                        grid[randomRow][randomCol] = SMOKE;
                        grid[randomRow + 1][randomCol] = SAND;
                    }
                    else
                    {
                        grid[randomRow][randomCol] = EMPTY;
                        grid[randomRow + 1][randomCol] = SAND;
                    }
                }
                if (grid[randomRow + 1][randomCol] == EXPLOSION_FIRE)
                {
                    int smokeChance = (int) (Math.random() * 4);

                    if (smokeChance == 0)
                    {
                        grid[randomRow][randomCol] = SMOKE;
                        grid[randomRow + 1][randomCol] = SAND;
                    }
                    else
                    {
                        grid[randomRow][randomCol] = EMPTY;
                        grid[randomRow + 1][randomCol] = SAND;
                    }
                }
            }
            else if (grid[randomRow][randomCol] == DIRT)
            {
                if (grid[randomRow + 1][randomCol] == EMPTY)
                {
                    grid[randomRow][randomCol] = EMPTY;
                    grid[randomRow + 1][randomCol] = DIRT;
                }
                if (grid[randomRow + 1][randomCol] == WATER)
                {
                    grid[randomRow][randomCol] = WATER;
                    grid[randomRow + 1][randomCol] = DIRT;
                }
                if (grid[randomRow + 1][randomCol] == SMOKE)
                {
                    grid[randomRow][randomCol] = SMOKE;
                    grid[randomRow + 1][randomCol] = DIRT;
                }
                if (grid[randomRow + 1][randomCol] == ACID)
                {
                    int fallthroughChance  = (int) (Math.random() * 2);

                    if (fallthroughChance == 0)
                    {
                        grid[randomRow][randomCol] = ACID;
                        grid[randomRow + 1][randomCol] = DIRT;
                    }
                    else
                    {
                        grid[randomRow][randomCol] = EMPTY;
                        grid[randomRow + 1][randomCol] = EMPTY;
                    }
                }
                if (grid[randomRow + 1][randomCol] == FIRE)
                {
                    int smokeChance = (int) (Math.random() * 8);

                    if (smokeChance == 0)
                    {
                        grid[randomRow][randomCol] = SMOKE;
                        grid[randomRow + 1][randomCol] = DIRT;
                    }
                    else
                    {
                        grid[randomRow][randomCol] = EMPTY;
                        grid[randomRow + 1][randomCol] = DIRT;
                    }
                }
                if (grid[randomRow + 1][randomCol] == EXPLOSION_FIRE)
                {
                    int smokeChance = (int) (Math.random() * 4);

                    if (smokeChance == 0)
                    {
                        grid[randomRow][randomCol] = SMOKE;
                        grid[randomRow + 1][randomCol] = DIRT;
                    }
                    else
                    {
                        grid[randomRow][randomCol] = EMPTY;
                        grid[randomRow + 1][randomCol] = DIRT;
                    }
                }
            }
            else if (grid[randomRow][randomCol] == PURPLE_VIRUS)
            {
                int fallthroughChance  = (int) (Math.random() * 2);

                if (grid[randomRow + 1][randomCol] == EMPTY)
                {
                    grid[randomRow][randomCol] = EMPTY;
                    grid[randomRow + 1][randomCol] = PURPLE_VIRUS;
                }
                if (grid[randomRow + 1][randomCol] == WATER)
                {
                    if (fallthroughChance == 0)
                    {
                        grid[randomRow][randomCol] = WATER;
                        grid[randomRow + 1][randomCol] = PURPLE_VIRUS;
                    }
                    else
                    {
                        grid[randomRow][randomCol] = PURPLE_VIRUS;
                        grid[randomRow + 1][randomCol] = PURPLE_VIRUS;
                    }
                }
                if (grid[randomRow + 1][randomCol] == SMOKE)
                {
                    if (fallthroughChance == 0)
                    {
                        grid[randomRow][randomCol] = SMOKE;
                        grid[randomRow + 1][randomCol] = PURPLE_VIRUS;
                    }
                    else
                    {
                        grid[randomRow][randomCol] = PURPLE_VIRUS;
                        grid[randomRow + 1][randomCol] = PURPLE_VIRUS;
                    }
                }
            }
        }

        if (grid[randomRow][randomCol] == WATER)
        {
            int randomDirection  = (int) (Math.random() * 3);

            if (randomRow != grid.length - 1)
            {
                if (grid[randomRow + 1][randomCol] == EMPTY || grid[randomRow + 1][randomCol] == SMOKE || grid[randomRow + 1][randomCol] == FIRE
                        || grid[randomRow + 1][randomCol] == EXPLOSION_FIRE || grid[randomRow + 1][randomCol] == ACID)
                {
                    randomDirection = 2;
                }
                else
                {
                    randomDirection = (int) (Math.random() * 2);
                }
            }
            
            if (randomDirection == 0 && randomCol + 1 != grid[0].length)	//Right
            {
                if (grid[randomRow][randomCol + 1] == EMPTY)
                {
                    grid[randomRow][randomCol + 1] = WATER;
                    grid[randomRow][randomCol] = EMPTY;
                }
                else if (grid[randomRow][randomCol + 1] == SMOKE)
                {
                    grid[randomRow][randomCol + 1] = WATER;
                    grid[randomRow][randomCol] = SMOKE;
                }
                else if (grid[randomRow][randomCol + 1] == FIRE)
                {
                    int smokeChance = (int) (Math.random() * 8);

                    if (smokeChance == 0)
                    {
                        grid[randomRow][randomCol + 1] = SMOKE;
                    }
                    else
                    {
                        grid[randomRow][randomCol + 1] = WATER;
                        grid[randomRow][randomCol] = EMPTY;
                    }
                }
                else if (grid[randomRow][randomCol + 1] == EXPLOSION_FIRE)
                {
                    int smokeChance = (int) (Math.random() * 8);

                    if (smokeChance == 0)
                    {
                        grid[randomRow][randomCol + 1] = SMOKE;
                    }
                    else
                    {
                        grid[randomRow][randomCol + 1] = WATER;
                        grid[randomRow][randomCol] = EMPTY;
                    }
                }
            }
            else if (randomDirection == 1 && randomCol - 1 != -1)	//Left
            {
                if (grid[randomRow][randomCol - 1] == EMPTY)
                {
                    grid[randomRow][randomCol - 1 ] = WATER;
                    grid[randomRow][randomCol] = EMPTY;
                }
                else if (grid[randomRow][randomCol - 1] == SMOKE)
                {
                    grid[randomRow][randomCol -1 ] = WATER;
                    grid[randomRow][randomCol] = SMOKE;
                }
                else if (grid[randomRow][randomCol - 1] == FIRE)
                {
                    int smokeChance = (int) (Math.random() * 8);

                    if (smokeChance == 0)
                    {
                        grid[randomRow][randomCol - 1] = SMOKE;
                    }
                    else
                    {
                        grid[randomRow][randomCol - 1] = WATER;
                        grid[randomRow][randomCol] = EMPTY;
                    }
                }
                else if (grid[randomRow][randomCol - 1] == EXPLOSION_FIRE)
                {
                    int smokeChance = (int) (Math.random() * 8);

                    if (smokeChance == 0)
                    {
                        grid[randomRow][randomCol - 1] = SMOKE;
                    }
                    else
                    {
                        grid[randomRow][randomCol - 1] = WATER;
                        grid[randomRow][randomCol] = EMPTY;
                    }
                }
            }
            else if (randomDirection == 2 && randomRow != grid.length - 1)	//Down
            {
                if (grid[randomRow + 1][randomCol] == EMPTY )
                {
                    grid[randomRow + 1][randomCol] = WATER;
                    grid[randomRow][randomCol] = EMPTY;
                }
                else if (grid[randomRow + 1][randomCol] == FIRE)
                {
                    int smokeChance = (int) (Math.random() * 8);

                    if (smokeChance == 0)
                    {
                        grid[randomRow + 1][randomCol] = SMOKE;
                    }
                    else
                    {
                        grid[randomRow + 1][randomCol] = WATER;
                        grid[randomRow][randomCol] = EMPTY;
                    }
                }
                else if (grid[randomRow + 1][randomCol] == EXPLOSION_FIRE)
                {
                    int smokeChance = (int) (Math.random() * 8);

                    if (smokeChance == 0)
                    {
                        grid[randomRow + 1][randomCol] = SMOKE;
                    }
                    else
                    {
                        grid[randomRow + 1][randomCol] = WATER;
                        grid[randomRow][randomCol] = EMPTY;
                    }
                }
                else if (grid[randomRow + 1][randomCol] == SMOKE)
                {
                    grid[randomRow + 1][randomCol] = WATER;
                    grid[randomRow][randomCol] = SMOKE;
                }
                else if (grid[randomRow + 1][randomCol] == ACID )
                {
                    grid[randomRow + 1][randomCol] = EMPTY;
                    grid[randomRow][randomCol] = EMPTY;
                }
            }
        }

        else if (grid[randomRow][randomCol] == SMOKE)
        {
            int randomDirection = (int) (Math.random() * 3);

            int dissipateChance = (int) (Math.random() * 75);

            if (dissipateChance == 0)
            {
                grid[randomRow][randomCol] = EMPTY;
            }

            if (randomDirection == 0 && randomCol + 1 != grid[0].length)	//Right
            {
                if (grid[randomRow][randomCol + 1] == EMPTY)
                {
                    grid[randomRow][randomCol + 1] = SMOKE;
                    grid[randomRow][randomCol] = EMPTY;
                }
            }
            else if (randomDirection == 1 && randomCol - 1 != -1)	//Left
            {
                if (grid[randomRow][randomCol - 1] == EMPTY)
                {
                    grid[randomRow][randomCol - 1 ] = SMOKE;
                    grid[randomRow][randomCol] = EMPTY;
                }
            }
            else if (randomDirection == 2 && randomRow != 0)	//Up
            {
                if (grid[randomRow - 1][randomCol] == EMPTY)
                {
                    grid[randomRow - 1][randomCol] = SMOKE;
                    grid[randomRow][randomCol] = EMPTY;
                }
            }
        }

        else if (grid[randomRow][randomCol] == FIRE)
        {
            int randomDirection = (int) (Math.random() * 3);

            if (randomRow != grid.length - 1 && grid[randomRow + 1][randomCol] == WOOD)
            {
                randomDirection = (int) (Math.random() * 4);
            }

            int removalChance = (int) (Math.random() * 15);

            int smokeChance = (int) (Math.random() * 60);

            if (randomRow != 0 && grid[randomRow - 1][randomCol] == EMPTY) //Stops smoke from spawning when the fire is at the top
            {
                if (smokeChance == 0)
                {
                    grid[randomRow - 1][randomCol] = SMOKE;
                }
            }

            if (randomDirection == 0 && randomCol + 1 != grid[0].length)	//Right
            {
                if (removalChance < 1)
                {
                    grid[randomRow][randomCol] = EMPTY;
                }

                else if (grid[randomRow][randomCol + 1] == EMPTY)
                {
                    grid[randomRow][randomCol + 1] = FIRE;
                    grid[randomRow][randomCol] = EMPTY;
                }

                else if (grid[randomRow][randomCol + 1] == WOOD)    //Test burning, edit later
                {
                    grid[randomRow][randomCol + 1] = FIRE;
                    grid[randomRow][randomCol] = FIRE;
                }
            }
            else if (randomDirection == 1 && randomCol - 1 != -1)	//Left
            {
                if (removalChance < 1)
                {
                    grid[randomRow][randomCol] = EMPTY;
                }

                else if (grid[randomRow][randomCol - 1] == EMPTY)
                {
                    grid[randomRow][randomCol - 1 ] = FIRE;
                    grid[randomRow][randomCol] = EMPTY;
                }
                else if (grid[randomRow][randomCol - 1] == WOOD)
                {
                    grid[randomRow][randomCol - 1 ] = FIRE;
                    grid[randomRow][randomCol] = FIRE;
                }
            }
            else if (randomDirection == 2 && randomRow != 0)	//Up
            {
                if (removalChance < 1)
                {
                    grid[randomRow][randomCol] = EMPTY;
                }

                else if (grid[randomRow - 1][randomCol] == EMPTY)
                {
                    grid[randomRow - 1][randomCol] = FIRE;
                    grid[randomRow][randomCol] = EMPTY;
                }
                else if (grid[randomRow - 1][randomCol] == WOOD)
                {
                    grid[randomRow - 1][randomCol] = FIRE;
                    grid[randomRow][randomCol] = FIRE;
                }
            }
            else if (randomDirection == 3 && randomRow != grid.length - 1)	//Down, this only happens when wood is below
            {
                if (removalChance < 1)
                {
                    grid[randomRow][randomCol] = EMPTY;
                }

                else if (grid[randomRow + 1][randomCol] == EMPTY)
                {
                    grid[randomRow + 1][randomCol] = FIRE;
                    grid[randomRow][randomCol] = EMPTY;
                }
                else if (grid[randomRow + 1][randomCol] == WOOD)
                {
                    grid[randomRow + 1][randomCol] = FIRE;
                    grid[randomRow][randomCol] = FIRE;
                }
            }
        }

        else if (grid[randomRow][randomCol] == PINK_VIRUS)
        {
            int randomDirection = (int) (Math.random() * 4);

            if (randomDirection == 0 && randomCol + 1 != grid[0].length)	//Right
            {
                if (grid[randomRow][randomCol + 1] != EMPTY && grid[randomRow][randomCol + 1] != PINK_VIRUS
                        && grid[randomRow][randomCol + 1] != OBSIDIAN)
                {
                    grid[randomRow][randomCol + 1] = PINK_VIRUS;
                }
            }
            else if (randomDirection == 1 && randomCol - 1 != -1)	//Left
            {
                if (grid[randomRow][randomCol - 1] != EMPTY && grid[randomRow][randomCol - 1] != PINK_VIRUS
                        && grid[randomRow][randomCol - 1] != OBSIDIAN)
                {
                    grid[randomRow][randomCol - 1 ] = PINK_VIRUS;
                }
            }
            else if (randomDirection == 2 && randomRow != 0)	//Up
            {
                if (grid[randomRow - 1][randomCol] != EMPTY && grid[randomRow - 1][randomCol] != PINK_VIRUS
                        && grid[randomRow - 1][randomCol] != OBSIDIAN)
                {
                    grid[randomRow - 1][randomCol] = PINK_VIRUS;
                }
            }
            else if (randomDirection == 3 && randomRow != grid.length - 1)	//Down
            {
                if (grid[randomRow + 1][randomCol] != EMPTY && grid[randomRow + 1][randomCol] != PINK_VIRUS
                        && grid[randomRow + 1][randomCol] != OBSIDIAN)
                {
                    grid[randomRow + 1][randomCol] = PINK_VIRUS;
                }
            }
        }

        else if (grid[randomRow][randomCol] == PURPLE_VIRUS)
        {
            int randomDirection = (int) (Math.random() * 4);

            if (randomDirection == 0 && randomCol + 1 != grid[0].length)	//Right
            {
                if (grid[randomRow][randomCol + 1] != EMPTY && grid[randomRow][randomCol + 1] != PURPLE_VIRUS
                        && grid[randomRow][randomCol + 1] != OBSIDIAN)
                {
                    grid[randomRow][randomCol + 1] = PURPLE_VIRUS;
                }
            }
            else if (randomDirection == 1 && randomCol - 1 != -1)	//Left
            {
                if (grid[randomRow][randomCol - 1] != EMPTY && grid[randomRow][randomCol - 1] != PURPLE_VIRUS
                        && grid[randomRow][randomCol - 1] != OBSIDIAN)
                {
                    grid[randomRow][randomCol - 1 ] = PURPLE_VIRUS;
                }
            }
            else if (randomDirection == 2 && randomRow != 0)	//Up
            {
                if (grid[randomRow - 1][randomCol] != EMPTY && grid[randomRow - 1][randomCol] != PURPLE_VIRUS
                        && grid[randomRow - 1][randomCol] != OBSIDIAN)
                {
                    grid[randomRow - 1][randomCol] = PURPLE_VIRUS;
                }
            }
            else if (randomDirection == 3 && randomRow != grid.length - 1)	//Down
            {
                if (grid[randomRow + 1][randomCol] != EMPTY && grid[randomRow + 1][randomCol] != PURPLE_VIRUS
                        && grid[randomRow + 1][randomCol] != OBSIDIAN)
                {
                    grid[randomRow + 1][randomCol] = PURPLE_VIRUS;
                }
            }
        }

        else if (grid[randomRow][randomCol] == ACID)
        {
            int randomDirection = (int) (Math.random() * 3);

            if (randomRow != grid.length - 1)
            {
                if (grid[randomRow + 1][randomCol] == EMPTY)
                {
                    randomDirection = 2;
                }
            }

            /*
            if (randomRow != 0)
            {
                if (grid[randomRow - 1][randomCol] == SAND || grid[randomRow - 1][randomCol] == WATER || grid[randomRow - 1][randomCol] == DIRT)
                {
                    grid[randomRow - 1][randomCol] = EMPTY;
                    grid[randomRow][randomCol] = EMPTY;
                }
            }
            */

            if (randomDirection == 0 && randomCol + 1 != grid[0].length)	//Right
            {
                if (grid[randomRow][randomCol + 1] == EMPTY)	//Normal liquid flow
                {
                    grid[randomRow][randomCol + 1] = ACID;
                    grid[randomRow][randomCol] = EMPTY;
                }
                else if (grid[randomRow][randomCol + 1] != ACID && grid[randomRow][randomCol + 1] != OBSIDIAN)	//Destroys block to the right
                {
                    grid[randomRow][randomCol + 1] = EMPTY;
                    grid[randomRow][randomCol] = EMPTY;
                }
            }
            else if (randomDirection == 1 && randomCol - 1 != -1)	//Left
            {
                if (grid[randomRow][randomCol - 1] == EMPTY)
                {
                    grid[randomRow][randomCol - 1 ] = ACID;
                    grid[randomRow][randomCol] = EMPTY;
                }
                else if (grid[randomRow][randomCol - 1] != ACID && grid[randomRow][randomCol - 1] != OBSIDIAN)
                {
                    grid[randomRow][randomCol - 1] = EMPTY;
                    grid[randomRow][randomCol] = EMPTY;
                }
            }
            else if (randomDirection == 2 && randomRow != grid.length - 1)	//Down
            {
                if (grid[randomRow + 1][randomCol] == EMPTY)
                {
                    grid[randomRow + 1][randomCol] = ACID;
                    grid[randomRow][randomCol] = EMPTY;
                }
                else if (grid[randomRow + 1][randomCol] != ACID  && grid[randomRow + 1][randomCol] != OBSIDIAN)
                {
                    grid[randomRow + 1][randomCol] = EMPTY;
                    grid[randomRow][randomCol] = EMPTY;
                }
            }
        }
        else if (grid[randomRow][randomCol] == BOMB)
        {
            int explosions = 0;

            Boolean exploding = false;

            if (randomCol + 1 != grid[0].length)    //Checks right side of the bomb
            {
                if (grid[randomRow][randomCol + 1] == FIRE || grid[randomRow][randomCol + 1] == EXPLOSION_FIRE)
                {
                    grid[randomRow][randomCol] = EXPLOSION_FIRE;

                    exploding = true;
                }
            }
            if (randomCol - 1 != -1)    //Checks left side of the bomb
            {
                if (grid[randomRow][randomCol - 1] == FIRE || grid[randomRow][randomCol - 1] == EXPLOSION_FIRE)
                {
                    grid[randomRow][randomCol] = EXPLOSION_FIRE;

                    exploding = true;
                }
            }
            if (randomRow != 0)    //Checks above the bomb
            {
                if (grid[randomRow - 1][randomCol] == FIRE || grid[randomRow - 1][randomCol] == EXPLOSION_FIRE)
                {
                    grid[randomRow][randomCol] = EXPLOSION_FIRE;

                    exploding = true;
                }
            }
            if (randomRow != grid.length - 1)    //Checks below the bomb
            {
                if (grid[randomRow + 1][randomCol] == FIRE || grid[randomRow + 1][randomCol] == EXPLOSION_FIRE)
                {
                    grid[randomRow][randomCol] = EXPLOSION_FIRE;

                    exploding = true;
                }
            }

            while (exploding == true)
            {
                int randomDirection = (int) (Math.random() * 4);

                randomRow = (int) (Math.random() * grid.length);
                randomCol = (int) (Math.random() * grid[0].length);

                if (grid[randomRow][randomCol] == EXPLOSION_FIRE)
                {
                    //grid[randomRow][randomCol] = FIRE;

                    if (randomDirection == 0 && randomCol + 1 != grid[0].length)    //Right
                    {
                        if (grid[randomRow][randomCol + 1] != OBSIDIAN) {
                            grid[randomRow][randomCol + 1] = EXPLOSION_FIRE;
                            explosions++;
                        }
                    } else if (randomDirection == 1 && randomCol - 1 != -1)    //Left
                    {
                        if (grid[randomRow][randomCol - 1] != OBSIDIAN) {
                            grid[randomRow][randomCol - 1] = EXPLOSION_FIRE;
                            explosions++;
                        }
                    } else if (randomDirection == 2 && randomRow != 0)    //Up
                    {
                        if (grid[randomRow - 1][randomCol] != OBSIDIAN) {
                            grid[randomRow - 1][randomCol] = EXPLOSION_FIRE;
                            explosions++;
                        }
                    } else if (randomDirection == 3 && randomRow != grid.length - 1)    //Down
                    {
                        if (grid[randomRow + 1][randomCol] != OBSIDIAN) {
                            grid[randomRow + 1][randomCol] = EXPLOSION_FIRE;
                            explosions++;
                        }
                    }

                    if (explosions > 1000) {
                        exploding = false;
                    }
                }
            }
        }

        else if (grid[randomRow][randomCol] == EXPLOSION_FIRE)
        {
            int randomDirection = (int) (Math.random() * 3);

            if (randomRow != grid.length - 1 && grid[randomRow + 1][randomCol] == WOOD)
            {
                randomDirection = (int) (Math.random() * 4);
            }

            int removalChance = (int) (Math.random() * 15);

            int smokeChance = (int) (Math.random() * 60);

            if (randomRow != 0 && grid[randomRow - 1][randomCol] == EMPTY) //Stops smoke from spawning when the fire is at the top
            {
                if (smokeChance == 0)
                {
                    grid[randomRow - 1][randomCol] = SMOKE;
                }
            }

            if (randomDirection == 0 && randomCol + 1 != grid[0].length)	//Right
            {
                if (removalChance < 1)
                {
                    grid[randomRow][randomCol] = EMPTY;
                }

                else if (grid[randomRow][randomCol + 1] == EMPTY)
                {
                    grid[randomRow][randomCol + 1] = EXPLOSION_FIRE;
                    grid[randomRow][randomCol] = EMPTY;
                }

                else if (grid[randomRow][randomCol + 1] == WOOD)    //Test burning, edit later
                {
                    grid[randomRow][randomCol + 1] = EXPLOSION_FIRE;
                    grid[randomRow][randomCol] = EXPLOSION_FIRE;
                }
            }
            else if (randomDirection == 1 && randomCol - 1 != -1)	//Left
            {
                if (removalChance < 1)
                {
                    grid[randomRow][randomCol] = EMPTY;
                }

                else if (grid[randomRow][randomCol - 1] == EMPTY)
                {
                    grid[randomRow][randomCol - 1 ] = EXPLOSION_FIRE;
                    grid[randomRow][randomCol] = EMPTY;
                }
                else if (grid[randomRow][randomCol - 1] == WOOD)
                {
                    grid[randomRow][randomCol - 1 ] = EXPLOSION_FIRE;
                    grid[randomRow][randomCol] = EXPLOSION_FIRE;
                }
            }
            else if (randomDirection == 2 && randomRow != 0)	//Up
            {
                if (removalChance < 1)
                {
                    grid[randomRow][randomCol] = EMPTY;
                }

                else if (grid[randomRow - 1][randomCol] == EMPTY)
                {
                    grid[randomRow - 1][randomCol] = EXPLOSION_FIRE;
                    grid[randomRow][randomCol] = EMPTY;
                }
                else if (grid[randomRow - 1][randomCol] == WOOD)
                {
                    grid[randomRow - 1][randomCol] = EXPLOSION_FIRE;
                    grid[randomRow][randomCol] = EXPLOSION_FIRE;
                }
            }
            else if (randomDirection == 3 && randomRow != grid.length - 1)	//Down, this only happens when wood is below
            {
                if (removalChance < 1)
                {
                    grid[randomRow][randomCol] = EMPTY;
                }

                else if (grid[randomRow + 1][randomCol] == EMPTY)
                {
                    grid[randomRow + 1][randomCol] = EXPLOSION_FIRE;
                    grid[randomRow][randomCol] = EMPTY;
                }
                else if (grid[randomRow + 1][randomCol] == WOOD)
                {
                    grid[randomRow + 1][randomCol] = EXPLOSION_FIRE;
                    grid[randomRow][randomCol] = EXPLOSION_FIRE;
                }
            }
        }
    }

    public int[][] getGrid()
    {
        return grid;
    }

    public void loadLevel(int numRows, int numCols)
    {
        FileDialog dialog = new FileDialog((Frame)null, "Select Level to Load");
        dialog.setMode(FileDialog.LOAD);
        dialog.setFile("*.txt");
        dialog.setVisible(true);
        String file = dialog.getFile();

        if (file == null)
        {
            System.out.println("You cancelled the choice");
        }
        else
        {
            System.out.println("You chose " + file);

            int[][] newGrid = new int[numRows][numCols];
            try
            {
                BufferedReader reader = new BufferedReader(new FileReader(file));
                String line = "";
                int row = 0;

                while((line = reader.readLine()) != null)
                {
                    String[] cols = line.split(",");
                    int col = 0;
                    for(String  c : cols)
                    {
                        newGrid[row][col] = Integer.parseInt(c);
                        col++;
                    }
                    row++;
                }
                reader.close();

                for(int gridRow = 0; gridRow < grid.length; gridRow++)
                {
                    for(int gridCol = 0; gridCol < grid[0].length; gridCol++)
                    {
                        grid[gridRow][gridCol] = newGrid[gridRow][gridCol];
                    }
                }
            }
            catch(FileNotFoundException e)
            {
                System.out.println("File not found!");
            }
            catch(IOException f)
            {
                System.out.println("I/O Error");
            }
        }
    }

    public void saveLevel()
    {
        String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss").format(Calendar.getInstance().getTime());

        StringBuilder builder = new StringBuilder();

        try
        {
            for(int row = 0; row < grid.length; row++)
            {
                for(int col = 0; col < grid[0].length; col++)
                {
                    builder.append(grid[row][col]+"");	//Appends to the output string
                    if(col < grid[0].length - 1)			//If this is not the last row element
                    {
                        builder.append(",");				//Then add a comma
                    }
                }
                builder.append("\n");					//Append new line at the end of the row
            }

            BufferedWriter writer = new BufferedWriter(new FileWriter("DustLevel" + timeStamp + ".txt"));
            writer.write(builder.toString());
            writer.close();
        }
        catch(FileNotFoundException e)
        {
            System.out.println("File not found!");
        }
        catch(IOException f)
        {
            System.out.println("I/O Error");
        }
    }

    public void clearLevel()
    {
        for (int row = 0; row < grid.length; row++)
        {
            for (int col = 0; col < grid[0].length; col++)
            {
                grid[row][col] = 0;
            }
        }
    }

    public void fillScreen(int tool)
    {
        for (int row = 0; row < grid.length; row++)
        {
            for (int col = 0; col < grid[0].length; col++)
            {
                grid[row][col] = tool;
            }
        }
    }

    public void start()
    {
        while (true) //Infinite loop
        {
            for (int index = 0; index < display.getSpeed(); index++)
            {
                updatePixels();
            }
            updateDisplay();
            display.repaint();
            display.pause(1);  //Wait for redrawing and for mouse

            int[] mouseLocation = display.getMouseLocation();
            if (mouseLocation != null)  //Test if mouse clicked
            {
                locationClicked(mouseLocation[0], mouseLocation[1], display.getTool());
            }
        }
    }
}
