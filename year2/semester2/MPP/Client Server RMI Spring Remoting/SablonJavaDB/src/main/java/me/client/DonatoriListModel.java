package me.client;

import me.entities.Donator;

import javax.swing.*;
import java.util.ArrayList;
import java.util.List;
import java.util.function.Predicate;

public class DonatoriListModel extends AbstractListModel {
    private List<String> donatori;
    @Override
    public int getSize() {
        return donatori.size();
    }

    @Override
    public Object getElementAt(int index) {
        return donatori.get(index);
    }

    public DonatoriListModel() {
        donatori = new ArrayList<>();
    }

    public List<String> getDonatori() {
        return donatori;
    }

    public void setDonatori(List<String> donatori) {
        this.donatori = donatori;
    }

    public void donatorAdded(String d) {
        donatori.add(d);
        fireContentsChanged(this, donatori.size()-1, donatori.size());
    }

    public void donatorUpdate(Donator d) {
        donatori.removeIf(s -> {
            String[] elems = s.split(" {2}");
            return d.getName().equals(elems[0]) && d.getPhoneNumber().equals(elems[2]);
        });
      //  donatori.remove(d.getName() + "  " + d.getAddress() + "  " + d.getPhoneNumber());
        donatori.add(d.getName() + "  " + d.getAddress() + "  " + d.getPhoneNumber());
        fireContentsChanged(this, donatori.size()-1, donatori.size());
    }
}
