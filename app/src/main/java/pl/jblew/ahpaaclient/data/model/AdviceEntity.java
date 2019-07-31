/***
 *     Ameryka Hospital Personalized advice system — the Android client app. This system allows
 *     doctors to securely and reliably send medical advices to patient's smartphones.
 *
 *     Copyright (C) 2019 by Jędrzej Lewandowski <jedrzejblew@gmail.com>
 *         (https://jedrzej.lewandowski.doctor)
 *
 *     This program is free software: you can redistribute it and/or modify
 *     it under the terms of the GNU General Public License as published by
 *     the Free Software Foundation, either version 3 of the License, or
 *     (at your option) any later version.
 *
 *     This program is distributed in the hope that it will be useful,
 *     but WITHOUT ANY WARRANTY; without even the implied warranty of
 *     MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 *     GNU General Public License for more details.
 *
 *     You should have received a copy of the GNU General Public License
 *     along with this program.  If not, see <https://www.gnu.org/licenses/>.
 */

package pl.jblew.ahpaaclient.data.model;

import android.text.format.DateFormat;
import androidx.annotation.Keep;

import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

@Keep
public class AdviceEntity {
  public String id;
  public String patientName;
  public String medicalprofessionalName;
  public String parentPhoneNumber;
  public String uid;
  public String advice;
  public long timestamp;
  
  public Date getDate() {
    return new Date(this.timestamp * 1000L);
  }
  
  @Override
  public boolean equals(Object o) {
    if (this == o) return true;
    if (o == null || getClass() != o.getClass()) return false;
    
    AdviceEntity that = (AdviceEntity) o;
    
    if (timestamp != that.timestamp) return false;
    if (!id.equals(that.id)) return false;
    if (!patientName.equals(that.patientName)) return false;
    if (!medicalprofessionalName.equals(that.medicalprofessionalName)) return false;
    if (!parentPhoneNumber.equals(that.parentPhoneNumber)) return false;
    if (uid != null ? !uid.equals(that.uid) : that.uid != null) return false;
    return advice.equals(that.advice);
  }
  
  @Override
  public int hashCode() {
    int result = id.hashCode();
    result = 31 * result + patientName.hashCode();
    result = 31 * result + medicalprofessionalName.hashCode();
    result = 31 * result + parentPhoneNumber.hashCode();
    result = 31 * result + (uid != null ? uid.hashCode() : 0);
    result = 31 * result + advice.hashCode();
    result = 31 * result + (int) (timestamp ^ (timestamp >>> 32));
    return result;
  }
}
