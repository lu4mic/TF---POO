package dados.drone;

import java.util.ArrayList;

public class DronesLista {

        private ArrayList<Drone> listaDrones;

        public DronesLista() {
            listaDrones = new ArrayList<>();
        }

        public boolean addDrone(Drone d) {
            return listaDrones.add(d);
        }

        public ArrayList<Drone> getListaDrones(){
            return listaDrones;
        }

        public void setListaDrones(ArrayList<Drone> listaDrones){
            this.listaDrones = listaDrones;
        }
}