package com.axis.springjwt.controllers;

import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.axis.springjwt.models.ERole;
import com.axis.springjwt.models.Resource;
import com.axis.springjwt.models.ResourcePerm;
import com.axis.springjwt.models.Role;
import com.axis.springjwt.models.RolePermission;
import com.axis.springjwt.models.User;
import com.axis.springjwt.payload.request.LoginRequest;
import com.axis.springjwt.payload.request.SignupRequest;
import com.axis.springjwt.payload.response.JwtResponse;
import com.axis.springjwt.payload.response.MessageResponse;
import com.axis.springjwt.repository.RoleRepository;
import com.axis.springjwt.repository.UserRepository;
import com.axis.springjwt.security.jwt.JwtUtils;
import com.axis.springjwt.security.services.AccessService;
import com.axis.springjwt.security.services.UserDetailsImpl;

@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("/api/auth")
public class AuthController {
	@Autowired
	AuthenticationManager authenticationManager;

	@Autowired
	UserRepository userRepository;

	@Autowired
	RoleRepository roleRepository;

	@Autowired
	PasswordEncoder encoder;

	@Autowired
	JwtUtils jwtUtils;
	
	@Autowired
	private AccessService accessService;

	@PostMapping("/signin")
	public ResponseEntity<?> authenticateUser(@Valid @RequestBody LoginRequest loginRequest) {

		Authentication authentication = authenticationManager.authenticate(
				new UsernamePasswordAuthenticationToken(loginRequest.getUsername(), loginRequest.getPassword()));

		SecurityContextHolder.getContext().setAuthentication(authentication);
		String jwt = jwtUtils.generateJwtToken(authentication);

		UserDetailsImpl userDetails = (UserDetailsImpl) authentication.getPrincipal();
		List<String> roles = userDetails.getAuthorities().stream().map(item -> item.getAuthority())
				.collect(Collectors.toList());

		return ResponseEntity.ok(
				new JwtResponse(jwt, userDetails.getId(), userDetails.getUsername(), userDetails.getEmail(), roles));
	}

	@PostMapping("/signup")
	public ResponseEntity<?> registerUser(@Valid @RequestBody SignupRequest signUpRequest) {
		if (userRepository.existsByUsername(signUpRequest.getUsername())) {
			return ResponseEntity
					.badRequest()
					.body(new MessageResponse("Error: Username is already taken!"));
		}

		if (userRepository.existsByEmail(signUpRequest.getEmail())) {
			return ResponseEntity
					.badRequest()
					.body(new MessageResponse("Error: Email is already in use!"));
		}

		// Create new user's account
		User user = new User(signUpRequest.getUsername(), 
							 signUpRequest.getEmail(),
							 encoder.encode(signUpRequest.getPassword()));

	
		String strRoles = "ROLE_USER";
		Set<Role> roles = new HashSet<>();
		
	
		Role userRole = roleRepository.findByroleName(strRoles)	;
		roles.add(userRole);
		
	/*
	 * String defaultRole = "3"; String userId = String.valueOf(user.getId()); Role
	 * userRole = new Role(1, userId, defaultRole); roleRepository.save(userRole);
	 * 
	 */
	
/*		if (strRoles == null) {
			Role userRole = roleRepository.findByroleName(roleName)
					.orElseThrow(() -> new RuntimeException("Error: Role is not found."));
			roles.add(userRole);
		} else {
			strRoles.forEach(role -> {
				switch (role) {
				case "admin":
					Role adminRole = roleRepository.findByroleName(roleName)
							.orElseThrow( new RuntimeException("Error: Role is not found."));
					roles.add(adminRole);

					break;
				
				  case "mod": Role modRole = roleRepository.findByName(ERole.ROLE_MODERATOR)
				  .orElseThrow( new RuntimeException("Error: Role is not found."));
				  roles.add(modRole);
				  
				  break;
				 
				default:
					Role userRole = roleRepository.findByroleName(ERole.ROLE_USER)
							.orElseThrow( new RuntimeException("Error: Role is not found."));
					roles.add(userRole);
				}
			});
}*/


//User.setRoles(roles1 roles);
userRepository.save(user);

return ResponseEntity.ok(new MessageResponse("User registered successfully!"));}


	@GetMapping("/getAllUsers")
	public List<User> getAllUsers() {

		return accessService.getAllUsers();
	}

//	***************Roles ****************************

	@GetMapping("/getByRoleName/{roleName}")
	public Role getByRoleName(@PathVariable String roleName) {

		return accessService.getByRoleName(roleName);
	}
	
	@GetMapping("/getAllRole")
	public List<Role> getAllRole() {

		return accessService.getAllRole();
	}

	@PostMapping("/addRole")
	public Role addRole(@RequestBody Role role) {

		return accessService.addRole(role);

	}
	

	@GetMapping("/getroleByRoleId/{roleID}")
	public Role getRoleByRoleID(@PathVariable int roleID) {

		return accessService.getRoleByRoleID(roleID);
	}
	
	// Resources*******************
	
	@GetMapping("/getAllResource")
	public List<Resource> getAllResource() {

		return accessService.getAllResource();
	}

	
	
	
	@PostMapping("/addResource")
	// @PreAuthorize("hasRole('SUPER_ADMIN') or hasRole('ADMIN')")
	public Resource addResource(@RequestBody Resource resource) {

		return accessService.addResource(resource);
	}

	@PostMapping("/grantPerm")
	// @PreAuthorize("hasRole('SUPER_ADMIN') or hasRole('ADMIN')")
	public RolePermission grantPermissions(@RequestBody RolePermission rolePermission) {

		return accessService.grantPermissions(rolePermission);
	}
	
	
	//permissions *************
	
	@PostMapping("/createRolewithPerm")
	public String createRoleWithPermissions(@RequestBody ResourcePerm resourcePerm) {

		System.out.println("inside ");
		Role role = new Role();

		role.setRoleName(resourcePerm.getRoleName());

		Role roledata = accessService.addRole(role);

		System.out.println(roledata.getRoleID());
		System.out.print(resourcePerm.getPermissionList());

		for (RolePermission p : resourcePerm.getPermissionList()) {

			p.setRoleId(roledata.getRoleID());
		}

		System.out.println(resourcePerm.getPermissionList());
		for (RolePermission p : resourcePerm.getPermissionList()) {
			accessService.grantPermissions(p);
		}
		return "successFully created";

	}

	@PutMapping("/updatepermissionsByRoleID")
	public String updatepermissionsByRoleIDAndResourceId(@RequestBody ResourcePerm resourcePerm) {

		System.out.println("inside update ");

		return accessService.updatepermissionsByRoleIDAndResourceId(resourcePerm);

	}

	
	  @GetMapping("/getPermissionsByRoleName/{roleName}")
	  public ResourcePerm getPermissionsByRoleName(@PathVariable String roleName){
		  
		  Role role=getByRoleName(roleName);
		  
		  List<RolePermission> rolepermlist=  accessService.getPermissionsByRoleId(role.getRoleID());
		  
		  ResourcePerm resourcePerm=new ResourcePerm();
		  
		  resourcePerm.setRoleName(roleName);
		  resourcePerm.setPermissionList(rolepermlist);
		  
		  
	  return resourcePerm;
	  }
	 
	  

	@GetMapping("/getpermissions/{roleID}")
	public List<RolePermission> getPermissionsByRoleId(@PathVariable int roleID) {

		return accessService.getPermissionsByRoleId(roleID);
	}
	
	
}
