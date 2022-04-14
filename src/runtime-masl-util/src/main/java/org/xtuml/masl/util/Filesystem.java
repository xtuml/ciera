package org.xtuml.masl.util;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.Serializable;
import java.io.UncheckedIOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Set;

import io.ciera.runtime.api.domain.Variable;
import io.ciera.runtime.api.types.Device;
import io.ciera.runtime.api.types.ReadWriteDevice;
import io.ciera.runtime.api.types.TimeStamp;

public class Filesystem {

  public Filesystem() {}

  public Filesystem(Object domain) {
    this();
  }

  public static enum File_Types {
    Socket,
    Symlink,
    File,
    Block,
    Directory,
    Character,
    FIFO;
  }

  public static class Rwx implements Serializable {

    private static final long serialVersionUID = 30835445L;

    // structure components
    private boolean read;
    private boolean write;
    private boolean execute;

    // constructors
    public Rwx() {
      this.read = false;
      this.write = false;
      this.execute = false;
    }

    // component accessors
    public boolean getRead() {
      return read;
    }

    public void setRead(boolean read) {
      this.read = read;
    }

    public boolean getWrite() {
      return write;
    }

    public void setWrite(boolean write) {
      this.write = write;
    }

    public boolean getExecute() {
      return execute;
    }

    public void setExecute(boolean execute) {
      this.execute = execute;
    }

    @Override
    public String toString() {
      return super.toString()
          + " ("
          + "read="
          + getRead()
          + ", write="
          + getWrite()
          + ", execute="
          + getExecute()
          + ")";
    }
  }

  public static class Permissions implements Serializable {

    private static final long serialVersionUID = -2114628010L;

    // structure components
    private boolean sticky;
    private boolean setuid;
    private boolean setgid;
    private Rwx user;
    private Rwx group;
    private Rwx other;

    // constructors
    public Permissions() {
      this.sticky = false;
      this.setuid = false;
      this.setgid = false;
      this.user = new Rwx();
      this.group = new Rwx();
      this.other = new Rwx();
    }

    // component accessors
    public boolean getSticky() {
      return sticky;
    }

    public void setSticky(boolean sticky) {
      this.sticky = sticky;
    }

    public boolean getSetuid() {
      return setuid;
    }

    public void setSetuid(boolean setuid) {
      this.setuid = setuid;
    }

    public boolean getSetgid() {
      return setgid;
    }

    public void setSetgid(boolean setgid) {
      this.setgid = setgid;
    }

    public Rwx getUser() {
      return user;
    }

    public void setUser(Rwx user) {
      this.user = user;
    }

    public Rwx getGroup() {
      return group;
    }

    public void setGroup(Rwx group) {
      this.group = group;
    }

    public Rwx getOther() {
      return other;
    }

    public void setOther(Rwx other) {
      this.other = other;
    }

    @Override
    public String toString() {
      return super.toString()
          + " ("
          + "sticky="
          + getSticky()
          + ", setuid="
          + getSetuid()
          + ", setgid="
          + getSetgid()
          + ", user="
          + getUser()
          + ", group="
          + getGroup()
          + ", other="
          + getOther()
          + ")";
    }
  }

  public static class File_Status implements Serializable {

    private static final long serialVersionUID = 989023419L;

    // structure components
    private File_Types file_type;
    private Permissions permissions;
    private int uid;
    private int gid;
    private int size;
    private TimeStamp access_time;
    private TimeStamp modification_time;
    private TimeStamp status_change_time;

    // constructors
    public File_Status() {
      this.file_type = null;
      this.permissions = new Permissions();
      this.uid = 0;
      this.gid = 0;
      this.size = 0;
      this.access_time = TimeStamp.ZERO;
      this.modification_time = TimeStamp.ZERO;
      this.status_change_time = TimeStamp.ZERO;
    }

    // component accessors
    public File_Types getFile_type() {
      return file_type;
    }

    public void setFile_type(File_Types file_type) {
      this.file_type = file_type;
    }

    public Permissions getPermissions() {
      return permissions;
    }

    public void setPermissions(Permissions permissions) {
      this.permissions = permissions;
    }

    public int getUid() {
      return uid;
    }

    public void setUid(int uid) {
      this.uid = uid;
    }

    public int getGid() {
      return gid;
    }

    public void setGid(int gid) {
      this.gid = gid;
    }

    public int getSize() {
      return size;
    }

    public void setSize(int size) {
      this.size = size;
    }

    public TimeStamp getAccess_time() {
      return access_time;
    }

    public void setAccess_time(TimeStamp access_time) {
      this.access_time = access_time;
    }

    public TimeStamp getModification_time() {
      return modification_time;
    }

    public void setModification_time(TimeStamp modification_time) {
      this.modification_time = modification_time;
    }

    public TimeStamp getStatus_change_time() {
      return status_change_time;
    }

    public void setStatus_change_time(TimeStamp status_change_time) {
      this.status_change_time = status_change_time;
    }

    @Override
    public String toString() {
      return super.toString()
          + " ("
          + "file_type="
          + getFile_type()
          + ", permissions="
          + getPermissions()
          + ", uid="
          + getUid()
          + ", gid="
          + getGid()
          + ", size="
          + getSize()
          + ", access_time="
          + getAccess_time()
          + ", modification_time="
          + getModification_time()
          + ", status_change_time="
          + getStatus_change_time()
          + ")";
    }
  }

  public static class Filesystem_Status implements Serializable {

    private static final long serialVersionUID = -1034135938L;

    // structure components
    private long total_bytes;
    private long free_bytes;
    private long available_bytes;
    private long total_nodes;
    private long free_nodes;
    private long available_nodes;
    private long max_filename_length;
    private boolean read_only;

    // constructors
    public Filesystem_Status() {
      this.total_bytes = 0L;
      this.free_bytes = 0L;
      this.available_bytes = 0L;
      this.total_nodes = 0L;
      this.free_nodes = 0L;
      this.available_nodes = 0L;
      this.max_filename_length = 0L;
      this.read_only = false;
    }

    // component accessors
    public long getTotal_bytes() {
      return total_bytes;
    }

    public void setTotal_bytes(long total_bytes) {
      this.total_bytes = total_bytes;
    }

    public long getFree_bytes() {
      return free_bytes;
    }

    public void setFree_bytes(long free_bytes) {
      this.free_bytes = free_bytes;
    }

    public long getAvailable_bytes() {
      return available_bytes;
    }

    public void setAvailable_bytes(long available_bytes) {
      this.available_bytes = available_bytes;
    }

    public long getTotal_nodes() {
      return total_nodes;
    }

    public void setTotal_nodes(long total_nodes) {
      this.total_nodes = total_nodes;
    }

    public long getFree_nodes() {
      return free_nodes;
    }

    public void setFree_nodes(long free_nodes) {
      this.free_nodes = free_nodes;
    }

    public long getAvailable_nodes() {
      return available_nodes;
    }

    public void setAvailable_nodes(long available_nodes) {
      this.available_nodes = available_nodes;
    }

    public long getMax_filename_length() {
      return max_filename_length;
    }

    public void setMax_filename_length(long max_filename_length) {
      this.max_filename_length = max_filename_length;
    }

    public boolean getRead_only() {
      return read_only;
    }

    public void setRead_only(boolean read_only) {
      this.read_only = read_only;
    }

    @Override
    public String toString() {
      return super.toString()
          + " ("
          + "total_bytes="
          + getTotal_bytes()
          + ", free_bytes="
          + getFree_bytes()
          + ", available_bytes="
          + getAvailable_bytes()
          + ", total_nodes="
          + getTotal_nodes()
          + ", free_nodes="
          + getFree_nodes()
          + ", available_nodes="
          + getAvailable_nodes()
          + ", max_filename_length="
          + getMax_filename_length()
          + ", read_only="
          + getRead_only()
          + ")";
    }
  }

  // TODO
  public static class FileAdapter extends ReadWriteDevice {

    public FileAdapter(String name, InputStream in, OutputStream out) {
      super(name, in, out);
    }
  }

  public void open_read(final String file_name, final Variable<Device> dev) {
    // TODO Insert your implementation here
    throw new UnsupportedOperationException();
  }

  public void open_write(final String file_name, final Variable<Device> dev) {
    // TODO Insert your implementation here
    throw new UnsupportedOperationException();
  }

  public void open_append(final String file_name, final Variable<Device> dev) {
    // TODO Insert your implementation here
    throw new UnsupportedOperationException();
  }

  public void open_truncate(final String file_name, final Variable<Device> dev) {
    // TODO Insert your implementation here
    throw new UnsupportedOperationException();
  }

  public void close(final Variable<Device> dev) {
    // TODO Insert your implementation here
    throw new UnsupportedOperationException();
  }

  public void set_position(final Device file, final int index) {
    // TODO Insert your implementation here
    throw new UnsupportedOperationException();
  }

  public void move_position(final Device file, final int no_chars) {
    // TODO Insert your implementation here
    throw new UnsupportedOperationException();
  }

  public int get_position(final Device file) {
    // TODO Insert your implementation here
    throw new UnsupportedOperationException();
  }

  public int get_length(final Device file) {
    // TODO Insert your implementation here
    throw new UnsupportedOperationException();
  }

  public int get_remaining(final Device file) {
    // TODO Insert your implementation here
    throw new UnsupportedOperationException();
  }

  public String read_file(final String file_name) {
    try {
      return Files.readString(Path.of(file_name));
    } catch (IOException e) {
      throw new UncheckedIOException(e);
    }
  }

  public void write_file(final String file_name, final String contents) {
    // TODO Insert your implementation here
    throw new UnsupportedOperationException();
  }

  public void truncate_file(final String file_name, final int size) {
    // TODO Insert your implementation here
    throw new UnsupportedOperationException();
  }

  public String calculateMD5(final String file_name) {
    // TODO Insert your implementation here
    throw new UnsupportedOperationException();
  }

  public boolean file_exists(final String file_name) {
    return new File(file_name).exists();
  }

  public void touch_file(final String file_name) {
    // TODO Insert your implementation here
    throw new UnsupportedOperationException();
  }

  public void move_file(final String source, final String destination) {
    try {
      Files.move(Path.of(source), Path.of(destination));
    } catch (IOException e) {
      throw new UncheckedIOException(e);
    }
  }

  public void copy_file(final String source, final String destination) {
    // TODO Insert your implementation here
    throw new UnsupportedOperationException();
  }

  public void copy_file_preserve(final String source, final String destination) {
    // TODO Insert your implementation here
    throw new UnsupportedOperationException();
  }

  public void append_file(final String source, final String destination) {
    // TODO Insert your implementation here
    throw new UnsupportedOperationException();
  }

  public void delete_file(final String file_name) {
    // TODO Insert your implementation here
    throw new UnsupportedOperationException();
  }

  public void set_current_directory(final String directory_name) {
    // TODO Insert your implementation here
    throw new UnsupportedOperationException();
  }

  public Set<String> list_directory(final String directory_name) {
    if (file_exists(directory_name)
        && get_file_status(directory_name).getFile_type() == File_Types.Directory) {
      return Set.of(new File(directory_name).list());
    } else {
      throw new IllegalArgumentException(
          "Argument does not name an existing directory: " + directory_name);
    }
  }

  public String get_current_directory() {
    // TODO Insert your implementation here
    throw new UnsupportedOperationException();
  }

  public void create_directory(final String directory_name) {
    // TODO Insert your implementation here
    throw new UnsupportedOperationException();
  }

  public void delete_directory(final String directory_name) {
    // TODO Insert your implementation here
    throw new UnsupportedOperationException();
  }

  public void delete_tree(final String root_name) {
    // TODO Insert your implementation here
    throw new UnsupportedOperationException();
  }

  public void copy_tree(final String source, final String destination) {
    // TODO Insert your implementation here
    throw new UnsupportedOperationException();
  }

  public void copy_tree_preserve(final String source, final String destination) {
    // TODO Insert your implementation here
    throw new UnsupportedOperationException();
  }

  public void set_umask(final Permissions umask) {
    // TODO Insert your implementation here
    throw new UnsupportedOperationException();
  }

  public void add_permissions(final String file_name, final Permissions permissions) {
    // TODO Insert your implementation here
    throw new UnsupportedOperationException();
  }

  public void remove_permissions(final String file_name, final Permissions permissions) {
    // TODO Insert your implementation here
    throw new UnsupportedOperationException();
  }

  public void set_permissions(final String file_name, final Permissions permissions) {
    // TODO Insert your implementation here
    throw new UnsupportedOperationException();
  }

  public void change_owner(final String file_name, final int uid) {
    // TODO Insert your implementation here
    throw new UnsupportedOperationException();
  }

  public void change_group(final String file_name, final int gid) {
    // TODO Insert your implementation here
    throw new UnsupportedOperationException();
  }

  public File_Status get_file_status(final String file_name) {
    if (file_exists(file_name)) {
      final File file = new File(file_name);
      final File_Status status = new File_Status();
      status.file_type = file.isDirectory() ? File_Types.Directory : File_Types.File; // TODO
      status.modification_time = new TimeStamp(file.lastModified() * 1000000l);
      // TODO finish implementing this status
      return status;
    } else {
      throw new IllegalArgumentException("File does not exist: " + file_name);
    }
  }

  public File_Status get_file_link_status(final String file_name) {
    // TODO Insert your implementation here
    throw new UnsupportedOperationException();
  }

  public Filesystem_Status get_filesystem_status(final String file_name) {
    // TODO Insert your implementation here
    throw new UnsupportedOperationException();
  }

  public String read_link(final String link_name) {
    // TODO Insert your implementation here
    throw new UnsupportedOperationException();
  }

  public void create_sym_link(final String existing_name, final String new_name) {
    // TODO Insert your implementation here
    throw new UnsupportedOperationException();
  }

  public void create_hard_link(final String existing_name, final String new_name) {
    // TODO Insert your implementation here
    throw new UnsupportedOperationException();
  }

  public String canonicalize_filename(final String file_name) {
    // TODO Insert your implementation here
    throw new UnsupportedOperationException();
  }

  public String get_directory(final String file_name) {
    // TODO Insert your implementation here
    throw new UnsupportedOperationException();
  }

  public String get_basename(final String file_name) {
    // TODO Insert your implementation here
    throw new UnsupportedOperationException();
  }
}
