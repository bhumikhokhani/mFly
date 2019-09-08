package io.github.controlwear.joystickdemo.data;

import io.github.controlwear.joystickdemo.MainActivity;

public class MSP extends MainActivity {
    public String get_data(int msg,int size,int[] values){
        String content;
        if(msg!=200) {
            content = "$M<";
            content += String.valueOf(size);
            content += String.valueOf(msg);
            if (msg == 200)
                for (int i = 0; i < 4; i++)
                    content += String.valueOf(values[i]);
            int temp = size ^ msg;
            int crc = ret_crc(temp, values);
            content += crc;
        }
        else
        {

            content = String.valueOf(values[0]);
            for(int i=1;i<5;i++) {
                content += "*";
                content+=String.valueOf(values[i]);
            }

        }
        return content;
    }

    int ret_crc(int c,int[] values)
    {
        int temp,x,y;
        for(int i=0;i<4;i++)
        {
            temp =values[i];
            y=1000;
            while(y!=0)
            {
                x=temp/y;
                c ^= x;
                y/=10;

            }
        }
        return c;
    }

}
