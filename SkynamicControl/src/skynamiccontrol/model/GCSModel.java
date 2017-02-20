package skynamiccontrol.model;

import skynamiccontrol.Timeline.Timeline;
import skynamiccontrol.view.status.StatusListContainer;

import java.beans.PropertyChangeSupport;
import java.util.*;

/**
 * Created by Elodie on 14/02/2017.
 */
public class GCSModel extends Observable{
    private final int nb_aircraft;
    private List<Aircraft> aircrafts;
    private StatusManager statusManager;
    private TimelineManager timelineManager;
  /*  private final Map<EventType, Boolean> eventAvailability;
    private final PropertyChangeSupport support;*/

    public GCSModel() {
        this.nb_aircraft = 0;
        this.aircrafts = new ArrayList<>();
        statusManager = new StatusManager(this);
        timelineManager = new TimelineManager(this);
    }

    public List<Aircraft> getAircrafts() {
        return aircrafts;
    }

    public void addAircraft(Aircraft aircraft) {
        this.aircrafts.add(aircraft);
        //aircraft.addPrivateObserver(this);
        statusManager.createView(aircraft);
        timelineManager.addAircraft(aircraft);
        this.setChanged();
        notifyObservers();
    }

    public void setStatusListContainer(StatusListContainer statusListContainer) {
        this.statusManager.setStatusListContainer(statusListContainer);
    }

    public void setTimeline(Timeline timeline) {
        this.timelineManager.setTimeline(timeline);
    }

    /*
    @Override
    public void update(Observable o, Object arg) {
        statusManager.updateView((Aircraft)o);
    }*/
}
