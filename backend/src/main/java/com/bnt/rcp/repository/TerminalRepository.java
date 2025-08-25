package com.bnt.rcp.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.bnt.rcp.entity.Terminal;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import java.util.List;
import java.util.Optional;

@Repository
public interface TerminalRepository extends JpaRepository<Terminal, String> {

    /**
     * Find terminal by terminal ID
     * @param terminalId the terminal ID
     * @return Optional terminal
     */
    Optional<Terminal> findByTerminalId(String terminalId);

    /**
     * Find all terminals that are not deleted
     * @param deleted the deleted flag to exclude
     * @return list of active terminals
     */
    List<Terminal> findAllByDeletedNot(Character deleted);

    /**
     * Find terminals by terminal code
     * @param code the terminal code
     * @return list of terminals with the given code
     */
    // List<Terminal> findByCode(String code);

    /**
     * Find terminals by institution group
     * @param institutionGroup the institution group
     * @return list of terminals in the institution group
     */
    List<Terminal> findByInstitutionGroup(Integer institutionGroup);

    /**
     * Find terminals by MAC address
     * @param macAddress the MAC address
     * @return Optional terminal
     */
    Optional<Terminal> findByMacAddress(String macAddress);

    /**
     * Find terminals by EPP serial number
     * @param eppSerialNumber the EPP serial number
     * @return Optional terminal
     */
    Optional<Terminal> findByEppSerialNumber(String eppSerialNumber);

    /**
     * Find terminals by branch sort code
     * @param branchSortCode the branch sort code
     * @return list of terminals with the given branch sort code
     */
    List<Terminal> findByBranchSortCode(Integer branchSortCode);

    /**
     * Check if terminal exists by terminal ID
     * @param terminalId the terminal ID
     * @return true if exists, false otherwise
     */
    boolean existsByTerminalId(String terminalId);

    /**
     * Check if terminal with MAC address exists (excluding given terminal ID)
     * @param macAddress the MAC address
     * @param terminalId the terminal ID to exclude
     * @return true if exists, false otherwise
     */
    @Query("SELECT COUNT(t) > 0 FROM Terminal t WHERE t.macAddress = :macAddress AND t.terminalId != :terminalId")
    boolean existsByMacAddressAndTerminalIdNot(@Param("macAddress") String macAddress, @Param("terminalId") String terminalId);

    /**
     * Check if terminal with EPP serial number exists (excluding given terminal ID)
     * @param eppSerialNumber the EPP serial number
     * @param terminalId the terminal ID to exclude
     * @return true if exists, false otherwise
     */
    @Query("SELECT COUNT(t) > 0 FROM Terminal t WHERE t.eppSerialNumber = :eppSerialNumber AND t.terminalId != :terminalId")
    boolean existsByEppSerialNumberAndTerminalIdNot(@Param("eppSerialNumber") String eppSerialNumber, @Param("terminalId") String terminalId);

    /**
     * Find terminals with specific attributes using native query for complex searches
     * @param institutionGroup the institution group
     * @param branchSortCode the branch sort code
     * @return list of terminals matching the criteria
     */
    @Query(value = "SELECT * FROM terminal t WHERE " +
            "(:institutionGroup IS NULL OR t.institution_group = :institutionGroup) AND " +
            "(:branchSortCode IS NULL OR t.branchSortcode = :branchSortCode) AND " +
            "t.deleted != 'Y'", nativeQuery = true)
    List<Terminal> findTerminalsByCriteria(@Param("institutionGroup") Integer institutionGroup,
                                          @Param("branchSortCode") Integer branchSortCode);

    /**
     * Count active terminals by institution group
     * @param institutionGroup the institution group
     * @return count of active terminals
     */
    @Query("SELECT COUNT(t) FROM Terminal t WHERE t.institutionGroup = :institutionGroup AND t.deleted != 'Y'")
    long countActiveTerminalsByInstitutionGroup(@Param("institutionGroup") Integer institutionGroup);

    /**
     * Find terminals that inherit address from location
     * @return list of terminals with inherit address flag set to true
     */
    List<Terminal> findByInheritAddressTrue();

    /**
     * Find terminals with RKI flag set
     * @return list of terminals with RKI flag true
     */
    List<Terminal> findByRkIFlagTrue();
}
