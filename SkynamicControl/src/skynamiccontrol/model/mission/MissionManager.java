package skynamiccontrol.model.mission;

import skynamiccontrol.communication.IvyManager;
import skynamiccontrol.model.Waypoint;

import java.util.ArrayList;

/**
 * Created by fabien on 13/02/17.
 */
public class MissionManager {
    private static String MISSION_CIRCLE_LOCAL = "MISSION_CIRCLE";
    private static String MISSION_CIRCLE_LLA = "MISSION_CIRCLE_LLA";
    private static String MISSION_GOTOWP_LOCAL = "MISSION_GOTO_WP";
    private static String MISSION_GOTOWP_LLA = "MISSION_GOTO_WP_LLA";
    private static int INDEX_BIT_LENGTH = 8;
    private int aircraftId;
    private ArrayList<Instruction> instructions;
    private int currentInstructionId;
    private int nextIndex;

    public MissionManager(int aircraftId) {
        this.aircraftId = aircraftId;
        instructions = new ArrayList<>();
        currentInstructionId = 0;
        nextIndex = 0;
    }

    public void insertInstruction(Instruction instruction, InsertMode insertMode) {
        String msg;
        if(instruction instanceof Circle) {
            msg = forgeCircleMessage((Circle) instruction, insertMode);
        } else if(instruction instanceof GoToWP) {
            msg = forgeGoToWPMessage((GoToWP) instruction, insertMode);
        } else if(instruction instanceof Path) {
            msg = forgePathMessage((Path) instruction, insertMode);
        } else if(instruction instanceof Segment) {
            msg = forgeSegmentMessage((Segment) instruction, insertMode);
        } else if(instruction instanceof Survey) {
            msg = forgeSurveyMessage((Survey) instruction, insertMode);
        } else {
            System.out.println("Type not known.");
            return;
        }

        if(IvyManager.getInstance().sendMessage(msg)) {     //if message sent
            switch(insertMode) {
                case APPEND:
                    appendInstruction(instruction);
                    break;
                case PREPEND:
                    prependInstruction(instruction);
                    break;
                case REPLACE_CURRENT:
                    replaceCurrentInstruction(instruction);
                    break;
                case REPLACE_ALL:
                    replaceAllInstructions(instruction);
                    break;
            }
        }
    }

    public void goToNextInstruction() {
        String msg = "NEXT_MISSION" + " " + aircraftId;
        IvyManager.getInstance().sendMessage(msg);
    }

    public ArrayList<Instruction> getInstructions() {
        return instructions;
    }

    public int getCurrentInstructionId() {
        return currentInstructionId;
    }

    private void appendInstruction(Instruction instruction) {
        instructions.add(instruction);
    }

    private void replaceCurrentInstruction(Instruction instruction) {

    }

    private void replaceAllInstructions(Instruction instruction) {

    }

    private void prependInstruction(Instruction instruction) {

    }

    private String forgeCircleMessage(Circle circle, InsertMode insertMode) {
        String msg = "";
        int index = getNextIndex();
        if(circle.getCenter().getCoordinateSystem() == Waypoint.CoordinateSystem.LLA) {
            msg = MISSION_CIRCLE_LLA + " " +
                    aircraftId + " " +
                    insertMode.getValue() + " " +
                    (int)(circle.getCenter().getLatitude() * 10000000) + " " +
                    (int)(circle.getCenter().getLongitude() * 10000000) + " " +
                    (int)(circle.getCenter().getAltitude()) + " " +
                    circle.getRadius() + " " +
                    circle.getDuration() + " " +
                    index;
        } else if(circle.getCenter().getCoordinateSystem() == Waypoint.CoordinateSystem.LOCAL) {
            msg = MISSION_CIRCLE_LOCAL + " " +
                    aircraftId + " " +
                    insertMode.getValue() + " " +
                    circle.getCenter().getLatitude() + " " +
                    circle.getCenter().getLongitude() + " " +
                    circle.getAltitude() + " " +
                    circle.getRadius() + " " +
                    circle.getDuration() + " " +
                    index;
        }
        return msg;
    }

    private String forgeGoToWPMessage(GoToWP goToWP, InsertMode insertMode) {
        String msg = "";
        int index = getNextIndex();
        if(goToWP.getWaypoint().getCoordinateSystem() == Waypoint.CoordinateSystem.LLA) {
            msg = MISSION_GOTOWP_LLA + " " +
                    aircraftId + " " +
                    insertMode.getValue() + " " +
                    (int)(goToWP.getWaypoint().getLatitude() * 10000000) + " " +
                    (int)(goToWP.getWaypoint().getLongitude() * 10000000) + " " +
                    (int)(goToWP.getWaypoint().getAltitude()) + " " +
                    goToWP.getDuration() + " " +
                    index;
        } else if(goToWP.getWaypoint().getCoordinateSystem() == Waypoint.CoordinateSystem.LOCAL) {
            msg = MISSION_GOTOWP_LOCAL + " " +
                    aircraftId + " " +
                    insertMode.getValue() + " " +
                    goToWP.getWaypoint().getLatitude() + " " +
                    goToWP.getWaypoint().getLongitude() + " " +
                    (int)(goToWP.getWaypoint().getAltitude()) + " " +
                    goToWP.getDuration() + " " +
                    index;
        }
        return msg;
    }

    private String forgePathMessage(Path path, InsertMode insertMode) {
        return "";
    }

    private String forgeSegmentMessage(Segment segment, InsertMode insertMode) {
        return "";
    }

    private String forgeSurveyMessage(Survey survey, InsertMode insertMode) {
        return "";
    }

    private int getNextIndex() {
        return nextIndex++ % (1<<INDEX_BIT_LENGTH);
    }

    public enum InsertMode {
        APPEND(0),
        PREPEND(1),
        REPLACE_CURRENT(2),
        REPLACE_ALL(3);

        private int value;
        InsertMode(int value) {
            this.value = value;
        }

        public int getValue() {
            return value;
        }
    }
}
