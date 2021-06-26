/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package logic;

import java.io.Serializable;

/**
 *
 * @author Jen
 */
public class Settings implements Serializable {

    private String TitleText;
    private String ProgramVersion;
    private String Licensee;

    public Settings(String TitleText, String ProgramVersion, String Licensee) {
        this.TitleText = TitleText;
        this.ProgramVersion = ProgramVersion;
        this.Licensee = Licensee;

    }

    public String getTitleText() {
        return TitleText;
    }

    public void setTitleText(String TitleText) {
        this.TitleText = TitleText;
    }

    public String getProgramVersion() {
        return ProgramVersion;
    }

    public void setProgramVersion(String ProgramVersion) {
        this.ProgramVersion = ProgramVersion;
    }

    public String getLicensee() {
        return Licensee;
    }

    public void setLicensee(String Licensee) {
        this.Licensee = Licensee;
    }

}
