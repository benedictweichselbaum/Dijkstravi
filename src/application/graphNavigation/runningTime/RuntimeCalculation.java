package application.graphNavigation.runningTime;

public class RuntimeCalculation {

    private long startTime;
    private long neededTime;

    public RuntimeCalculation(){
        startTime=System.currentTimeMillis();
    }

    public void stopCalculation(){
        long endTime=System.currentTimeMillis();
        neededTime=endTime-startTime;
    }

    public String getResult(){
        String res;
        if(neededTime>1000){
            res = neededTime/1000 + "s";
        }
        else {
            res = neededTime + "ms";
        }
        return res;
    }
}
