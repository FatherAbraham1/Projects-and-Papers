package aos.server.bean;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.concurrent.ConcurrentHashMap;

import aos.bean.Peer;

public class Registry implements Serializable {

	// To assign the class Registry a version number in order to avoid
	// InvalidClassExceptions
	private static final long serialVersionUID = 126985638298L;

	private ConcurrentHashMap<String, ArrayList<Peer>> fileIndex;

	/**
	 * To add multiple files in the registry with same peer location
	 * 
	 * @param peer
	 *            Peer location where all files are residing
	 * @param files
	 *            ArrayList of files to be regitered
	 */
	public void register(Peer peer, ArrayList<String> files) {
		for (String file : files) {
			// Call add action for each file
			add(file, peer);
		}
	}

	/**
	 * It is a core method to add one file with its peer location to the
	 * registry; If there is no existing entry for the file, new entry will be
	 * made; If there is already an entry for same file in registry for same
	 * file, then only a peer entry will be added; If there is already an exact
	 * file,peer entry then there will be no changes in the registry.
	 * 
	 * @param file
	 *            File name to be entered in the registry
	 * @param peer
	 *            Peer location of file to be entered in the registry
	 */
	public void add(String file, Peer peer) {
		if (file != null && file != "") {
			ArrayList<Peer> filePeers = new ArrayList<Peer>();
			// Check whether entire registry is empty
			if (fileIndex == null) {
				filePeers.add(peer);
				fileIndex = new ConcurrentHashMap<String, ArrayList<Peer>>();
				fileIndex.put(file, filePeers);
			}
			// Check for existing entry in file
			else if (fileIndex.containsKey(file)) {
				filePeers = fileIndex.get(file);
				if (!filePeers.contains(peer))
					filePeers.add(peer);
			}
			// No entry found; Hence adding new entry
			else {
				filePeers.add(peer);
				fileIndex.put(file, filePeers);
			}
		}
	}

	/**
	 * Removing an entry of file from registry in case the file no longer exists
	 * on that peer location; If the file entry exists with multiple peer
	 * location entries, only the location entry will be removed. If the
	 * matching entry is the only entry for file, entire entry for the file will
	 * be removed.
	 * 
	 * @param file
	 *            : Name of file whose entry to be removed
	 * @param peer
	 *            : Location where file was made entry for
	 */
	public void remove(String file, Peer peer) {
		ArrayList<Peer> filePeers = fileIndex.get(file);
		// Peer entry will be removed if present
		if (filePeers!=null)
		filePeers.remove(peer);
		// If no other peer entries are remaining for the file, file entry will
		// be removed
		if (filePeers!=null&&filePeers.size() == 0)
			fileIndex.remove(file);
	}

	/**
	 * Searches for files location entries in the registry;
	 * 
	 * @param fileName
	 *            Name of file to be searched
	 * @return Returns arraylist containing all the peer locations where the
	 *         file with same names resides.
	 */
	public ArrayList<Peer> search(String fileName) {
		ArrayList<Peer> filePeers = null;
		if(fileIndex==null)
			return null;
		if (fileIndex.containsKey(fileName))
			filePeers = fileIndex.get(fileName);
		return filePeers;
	}

	/**
	 * To get object of registry
	 * 
	 * @return returns registry instance
	 */
	public ConcurrentHashMap<String, ArrayList<Peer>> getFileIndex() {
		return fileIndex;
	}

	@Override
	/**
	 *  To get String representation of registry
	 * @return returns string representation of registry
	 */
	public String toString() {
		return "Registry \n[\nfileIndex=" + fileIndex + "\n]";
	}

}